package br.com.telesul.reporting.model;

import java.text.ParseException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import br.com.telesul.reporting.util.FormatadorDeStrings;

@Entity
@Table(name = "tbl_atributosdaspesquisa")
@NamedQueries({
	 @NamedQuery(name = "AtributosDaPesquisa.listar", query = "SELECT atributosdapesquisa FROM AtributosDaPesquisa atributosdapesquisa")
	})
public class AtributosDaPesquisa implements Comparable<AtributosDaPesquisa>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "atr_codigo")
	private Long codigo;
	
	@Column(name="atr_coluna", nullable = false)
	private int coluna;
	
	@Column(name="atr_alias", length = 100, nullable = false)
	private String alias;
	
	@Column(name="atr_dummy_alias", length = 100, nullable = false) //apenas para header do relatorio
	private String dummyAlias;
	
	@Column(name="atr_tipo", length = 35, nullable = false)
	private String tipo;
	
	@Column(name="atr_mostra_coluna", nullable = false)
	private String mostraColuna;
	
	@Column(name="atr_clausula", length = 100, nullable = true)
	private String clausula;
	
	@Column(name="atr_operacao", length = 100, nullable = true)
	private String operacao;
		
	@Column(name="atr_resultadostr", length = 35, nullable = true)
	private String resultadoStr;
	
	@Column(name="atr_resultado", nullable = true)
	private double resultado;
	
	@Column(name="atr_datainicio_str", nullable = true)
	private String dataInicioPesquisaStr;
	
	@Column(name="atr_datafim_str", nullable = true)
	private String dataFimPesquisaStr;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tbl_relatorio_rel_codigo", referencedColumnName = "rel_codigo")	
	private Relatorio relatorio;
	
	@Column(name="atr_datainicio", nullable = true)
	private Date dataInicio;
	
	@Column(name="atr_datafim", nullable = true)
	private Date dataFim;
			
	public AtributosDaPesquisa(int coluna, String alias, String tipo, String  mostraColuna){
		this.coluna = coluna;
		this.alias = alias;
		this.dummyAlias = alias;
		this.tipo = tipo;
		this.resultado = 0;
		this.mostraColuna = mostraColuna;
		this.dataInicioPesquisaStr = FormatadorDeStrings.todaysDate(); // para nao dar null pointer na consulta
		this.dataFimPesquisaStr = FormatadorDeStrings.todaysDate();
	}
	
	public AtributosDaPesquisa(){
		
	}
	
	public String getOperacao() {
		return operacao;
	}
	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	
	
	public String getDummyAlias() {
		return dummyAlias;
	}

	public void setDummyAlias(String dummyAlias) {
		this.dummyAlias = dummyAlias;
	}

	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public String getResultadoStr() {
		return resultadoStr;
	}

	public void setResultadoStr(String resultadoStr) {
		this.resultadoStr = resultadoStr;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public String getClausula() {
		return clausula;
	}
	public void setClausula(String clausula) {
		this.clausula = clausula;
	}
	
	
	
	public String getDataInicioPesquisaStr() {
		return dataInicioPesquisaStr;
	}

	public void setDataInicioPesquisaStr(String dataInicioPesquisaStr) {
		this.dataInicioPesquisaStr = dataInicioPesquisaStr;
	}

	public String getDataFimPesquisaStr() {
		return dataFimPesquisaStr;
	}

	public void setDataFimPesquisaStr(String dataFimPesquisaStr) {
		this.dataFimPesquisaStr = dataFimPesquisaStr;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	
	
	
	public String getMostraColuna() {
		return mostraColuna;
	}

	public void setMostraColuna(String mostraColuna) {
		this.mostraColuna = mostraColuna;
	}

	public void  setDataStr(String data, int indexCalendario){
		if(tipo.equals("dataInicio")){
			setDataInicioPesquisaStr(data);
		}else if(tipo.equals("dataEntre")){
			if(indexCalendario == 0){
				setDataInicioPesquisaStr(data);
			}else{
				setDataFimPesquisaStr(data);
			}
		}else{
			setDataFimPesquisaStr(data);
		}
	}

	public void setaClausulaDeDataStr() throws ParseException{
		if(tipo.equals("dataInicio")){
			this.clausula = ">= " + this.dataInicioPesquisaStr;
		}else  if(tipo.equals("dataEntre")){
			this.clausula = "BETWEEN " + this.dataInicioPesquisaStr+" AND "+this.dataFimPesquisaStr;
		}else{
			this.clausula= "<= "+ this.dataFimPesquisaStr;
		}
	}
	
	public void setaClausulaDeData(){
		this.dataInicio = FormatadorDeStrings.toDate(this.dataInicioPesquisaStr);
		this.dataFim = FormatadorDeStrings.toDate(this.dataFimPesquisaStr);
	}
	
	

	@Override
	public int compareTo(AtributosDaPesquisa adp) {
		Integer o1 = new Integer(this.getColuna());
		Integer o2 = new Integer(adp.getColuna());
		
		return o1.compareTo(o2);
	}

	
	
	
}
