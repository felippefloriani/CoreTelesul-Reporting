package br.com.telesul.reporting.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.dao.CalculadoraDao;
import br.com.telesul.reporting.exception.ParametrosOperacaoMatematicaErrada;
import br.com.telesul.reporting.model.AtributosDaPesquisa;

public class CalculadorDeOperacoes {

	private String operacao;
	private List<String[]> pesquisaList;
	private Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa;
	private boolean temOperacao = false;
	private CalculadoraDao dao;

	public CalculadorDeOperacoes(List<String[]> pesquisaList, Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa)	throws SQLException, ParseException, ParametrosOperacaoMatematicaErrada {
		this.pesquisaList = pesquisaList;
		this.mapaAtributosPesquisa = mapaAtributosPesquisa;
		calcula();
	}

	private void calcula() throws SQLException, ParseException, ParametrosOperacaoMatematicaErrada {
		geraExpressaoDaOperacao();
		if(temOperacao){
			dao = new CalculadoraDao(mapaAtributosPesquisa);
			this.mapaAtributosPesquisa = dao.getMapaAtributosPesquisa();
		}
	}
	
	private List<String> achaCabecalho(){
		List<String> header = new ArrayList<String>();
		for (Map.Entry<Integer, AtributosDaPesquisa> entry : mapaAtributosPesquisa.entrySet()){
			AtributosDaPesquisa adp = entry.getValue();
			header.add(adp.getAlias());
		}
		return header;
	}
	
	private void geraExpressaoDaOperacao() throws ParametrosOperacaoMatematicaErrada {
		for (Map.Entry<Integer, AtributosDaPesquisa> entry : mapaAtributosPesquisa.entrySet()) {
			AtributosDaPesquisa ap = entry.getValue();
			operacao = ap.getOperacao();
			if(StringUtils.isNotBlank(operacao)){
				temOperacao = true;
				operacao= operacao.toUpperCase();
				operacao = operacao.replaceAll(" ", "");
				while (true) {
					if (ValidadorDeOperacoes.isNumeroOuOperador(operacao)) {
						ap.setResultadoStr(operacao);
						break;
					}
					escolheOperacao(ap.getTipo());
				}
			}
		}
	}

	private String escolheOperacao(String tipo) throws ParametrosOperacaoMatematicaErrada {
		String resultado = null;
		String conteudo = "";
		if (operacao.contains("COUNT")) {
			conteudo = "COUNT";
			resultado = calculaCount();
		} else if (operacao.contains("SUM")) {
			conteudo = "SUM";
			resultado = calculaSum(tipo);
		} else if (operacao.contains("AVG")) {
			conteudo = "AVG";
			resultado = calculaAvg(tipo);
		} else if (operacao.contains("MAX")) {
			conteudo = "MAX";
			resultado = calculaMax(tipo);
		} else if (operacao.contains("MIN")) {
			conteudo = "MIN";
			resultado = calculaMin(tipo);
		}
		operacao = ValidadorDeOperacoes.tiraNomesEspeciais(operacao, conteudo,resultado);
		return resultado;
	}
	
	private List<Double> stringToSecs(List<String> lista){
		List<Double> secsList = new ArrayList<Double>();
		
		for(String str : lista){
			String[] arr = str.split(":");
			double hour = Double.parseDouble(arr[0])*3600;
			double min =  Double.parseDouble(arr[1])*60;
			double sec =  Double.parseDouble(arr[2]);
			
			double totalSecs = hour+min+sec;
			secsList.add(totalSecs);
		}
		
		return secsList;
	}

	private String calculaCount() {
		
		List<String> lista = arrToList();
		return String.valueOf(lista.size());
	}

	private String calculaSum(String tipo) throws ParametrosOperacaoMatematicaErrada {
		double sum=0;
		List<String> listaStr = arrToList();
		

		if(tipo.equals("texto")){
			throw new ParametrosOperacaoMatematicaErrada();
		}
		
		if(tipo.equals("time")){
			List<Double> listDouble= stringToSecs(listaStr);
			sum = listDouble.stream().mapToDouble(a -> a.doubleValue()).sum();
			return FormatadorDeStrings.formataTempo(sum);
			
		}else{
			sum = listaStr.stream().mapToDouble(a -> Double.parseDouble(a)).sum();
		}
		return FormatadorDeStrings.formataDecimal(String.valueOf(sum),"00");
	}

	private String calculaAvg(String tipo)throws ParametrosOperacaoMatematicaErrada {
		OptionalDouble average = null;
		List<String> listaStr = arrToList();
		
		if(tipo.equals("texto")){
			throw new ParametrosOperacaoMatematicaErrada();
		}
		
		if(tipo.equals("time")){
			List<Double> listDouble= stringToSecs(listaStr);
			average = listDouble.stream().mapToDouble(a -> a.doubleValue()).average();
		}else{
			average = listaStr.stream().mapToDouble(a -> Double.parseDouble(a)).average();
		}
		return FormatadorDeStrings.formataDecimal(String.valueOf(average.isPresent() ? average.getAsDouble() : 0),"00");
	}

	private String calculaMax(String tipo) throws ParametrosOperacaoMatematicaErrada {
		List<String> listaStr = arrToList();
		OptionalDouble max = null;
		

		if(tipo.equals("texto")){
			throw new ParametrosOperacaoMatematicaErrada();
		}
		
		if(tipo.equals("time")){
			List<Double> listDouble= stringToSecs(listaStr);
			max = listDouble.stream().mapToDouble(a -> a.doubleValue()).max();
		}else{
			max = listaStr.stream().mapToDouble(a -> Double.parseDouble(a)).max();
		}

		return FormatadorDeStrings.formataDecimal(String.valueOf(max.isPresent() ? max.getAsDouble() : 0),"00");
	}

	private String calculaMin(String tipo) throws ParametrosOperacaoMatematicaErrada {
		List<String> lista = arrToList();
		OptionalDouble min = null;
		

		if(tipo.equals("texto")){
			throw new ParametrosOperacaoMatematicaErrada();
		}
		
		if(tipo.equals("time")){
			List<Double> listDouble = stringToSecs(lista);
			min = listDouble.stream().mapToDouble(a -> a.doubleValue()).min();
		}else{
			min = lista.stream().mapToDouble(a -> Double.parseDouble(a)).min();
		}

		return FormatadorDeStrings.formataDecimal(String.valueOf(min.isPresent() ? min.getAsDouble() : 0),"00");
	}

	private List<String> arrToList() {
		int coluna = getColunaDaOperacao();
		String[] auxArr = new String[pesquisaList.size()];
		for (int i = 0; i < pesquisaList.size(); i++) {
			auxArr[i] = (pesquisaList.get(i)[coluna]);
		}
		return Arrays.asList(auxArr);
	}

	private int getColunaDaOperacao() {
		int inicio = operacao.indexOf("(") + 1;
		int fim = operacao.indexOf(")");
		String aux = operacao.substring(inicio, fim);
		return achaCabecalho().indexOf(aux);
	}

	public Map<Integer, AtributosDaPesquisa> getMapaAtributosPesquisa() {
		return mapaAtributosPesquisa;
	}

	public void setMapaAtributosPesquisa(
			Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) {
		this.mapaAtributosPesquisa = mapaAtributosPesquisa;
	}

	/*
	 * 
	 * debug
	 * 
	 * public CalculaOperacao() throws ClassNotFoundException, SQLException{
	 * this.pesquisaList = simulaLista(); this.mapaAtributosPesquisa =
	 * simulaMapa(); this.header = simulaHeader(); geraExpressaoDaOperacao();
	 * calcula(); }
	 * 
	 * private String[] simulaHeader(){ String[] arr = {"NOME", "IDADE",
	 * "SALARIO","DEPENDENTES"}; return arr; }
	 * 
	 * private List<String[]> simulaLista(){ List<String[]> pesquisaList = new
	 * ArrayList<String[]>(); String[] arr1 = {"fulano", "25" , "3000", "3"};
	 * String[] arr2 = {"beltrano", "30", "4000", "2"}; String[] arr3 =
	 * {"sicrano", "54", "50000", "5"}; pesquisaList.add(arr1);
	 * pesquisaList.add(arr2); pesquisaList.add(arr3); return pesquisaList; }
	 * 
	 * private Map<Integer, AtributosPesquisa> simulaMapa(){
	 * 
	 * Map<Integer, AtributosPesquisa> mapaAtributosPesquisa = new
	 * TreeMap<Integer, AtributosPesquisa>();
	 * 
	 * AtributosPesquisa ap1 = new AtributosPesquisa("Nome", "Nome", "texto");
	 * ap1.setOperacao("");
	 * 
	 * AtributosPesquisa ap2 = new AtributosPesquisa("Idade", "idade",
	 * "inteiro"); ap2.setOperacao("MAX(idade)/2 + 5*SUM(salario)");
	 * 
	 * AtributosPesquisa ap3 = new AtributosPesquisa("Salario", "salario",
	 * "monetario"); ap3.setOperacao("SUM(salario)");
	 * 
	 * AtributosPesquisa ap4 = new AtributosPesquisa("Dependentes",
	 * "Depententes", "inteiro"); ap4.setOperacao("AVG(dependentes)");
	 * 
	 * mapaAtributosPesquisa.put(0, ap1); mapaAtributosPesquisa.put(1, ap2);
	 * mapaAtributosPesquisa.put(2, ap3); mapaAtributosPesquisa.put(3, ap4);
	 * 
	 * return mapaAtributosPesquisa; }
	 * 
	 * public static void main(String[] args) throws ClassNotFoundException,
	 * SQLException { new CalculaOperacao(); }
	 */
}
