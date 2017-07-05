package br.com.telesul.reporting.util;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.omnifaces.util.Messages;

import br.com.telesul.reporting.dao.RelatorioQueryDao;
import br.com.telesul.reporting.documentos.FabricaDePdfs;
import br.com.telesul.reporting.exception.ParametrosOperacaoMatematicaErrada;
import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.model.Relatorio;

public class PdfUtil {
	
	public static void criaPdf(Relatorio relatorio,List<AtributosDaPesquisa> atributosDaPesquisaList ) throws SQLException, ParseException, ParametrosOperacaoMatematicaErrada{
		criaPdf(relatorio.getConexao(), relatorio.getNome(), relatorio.getDescricao(), relatorio.getQuery(), constroiMapa(atributosDaPesquisaList));
	}
	
	public static void criaPdf(Conexao conexao, String nome, String descricao, String query, Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) throws SQLException, ParseException, ParametrosOperacaoMatematicaErrada{
		RelatorioQueryDao relatorioDao = new RelatorioQueryDao(conexao , query, mapaAtributosPesquisa);
		List<String[]> resultadoDaConsulta = relatorioDao.getResultadoDaConsulta();
			
		CalculadorDeOperacoes calculadora = new CalculadorDeOperacoes(resultadoDaConsulta, mapaAtributosPesquisa);
		mapaAtributosPesquisa = calculadora.getMapaAtributosPesquisa();
			
		new FabricaDePdfs(nome, descricao, resultadoDaConsulta, mapaAtributosPesquisa);
		
			
		
	}
	
	private static Map<Integer, AtributosDaPesquisa> constroiMapa(List<AtributosDaPesquisa> atributosDaPesquisaList){
		Map<Integer, AtributosDaPesquisa> mapa = new HashMap<Integer, AtributosDaPesquisa>();
		for(AtributosDaPesquisa ap: atributosDaPesquisaList){
			mapa.put(ap.getColuna(), ap);
		}
		return mapa;
	}

}
