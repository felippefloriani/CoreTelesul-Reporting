package br.com.telesul.reporting.bean;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import br.com.telesul.core.dao.ConexaoLocalDAO;
import br.com.telesul.reporting.dao.AtributosDaPesquisaDAO;
import br.com.telesul.reporting.dao.GrupoRelatorioDAO;
import br.com.telesul.reporting.dao.RelatorioDAO;
import br.com.telesul.reporting.dao.SubGrupoRelatorioDAO;
import br.com.telesul.reporting.exception.ParametrosOperacaoMatematicaErrada;
import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.model.GrupoRelatorio;
import br.com.telesul.reporting.model.Relatorio;
import br.com.telesul.reporting.model.SubGrupoRelatorio;
import br.com.telesul.reporting.util.DefinidorDeColunas;
import br.com.telesul.reporting.util.PdfUtil;
import br.com.telesul.reporting.util.ValidadorDeOperacoes;

@ManagedBean
@SessionScoped
public class RelatorioBean  {

	private String nome;
	private String descricao;
	private String query;
	
	private String[] colunasArr;
	private SelectItem[] tipos;
		
	private boolean clicouEmGerarTotalizadores;
	private int colunaQueEstaSendoDetalhada;
	
	private List<GrupoRelatorio> listaGrupoRelatorios;
	private List<SubGrupoRelatorio> listaSubGrupoRelatorios;
	private List<Conexao> listaConexoes;
		
	private GrupoRelatorio grupoRelatorioSelecionado;
	private SubGrupoRelatorio subGrupoRelatorioSelecionado;
	private Relatorio relatorio;
	private Conexao conexaoSelecionada;
	
	private Map<Integer,AtributosDaPesquisa> mapaAtributosPesquisa = new HashMap<Integer, AtributosDaPesquisa>();
	
	@PostConstruct
	public void listar() {
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			listaGrupoRelatorios = grupoRelatorioDAO.listar();
			
			ConexaoLocalDAO conexaoDAO = new ConexaoLocalDAO();
			listaConexoes = conexaoDAO.listar();
			
			listaSubGrupoRelatorios = new ArrayList<SubGrupoRelatorio>();
			
		} catch (RuntimeException ex) {
			Messages.addGlobalInfo("Erro ao tentar inicializar os componentes da página");
		}
	}
	
	public void carregaSubGrupoRelatorio(){
		SubGrupoRelatorioDAO subGrupoRelatorioDAO = new SubGrupoRelatorioDAO();
		listaSubGrupoRelatorios = subGrupoRelatorioDAO.buscaPorGrupoRelatorio(grupoRelatorioSelecionado);
		refreshPage();
	}
	
	public void inicializaComponentes(){
			colunasArr = DefinidorDeColunas.separaColunas(query);
			AtributosDaPesquisa ap;
			for(int i=0; i< colunasArr.length; i++){
				ap = new AtributosDaPesquisa(i,colunasArr[i],"texto", "mostrar");
				mapaAtributosPesquisa.put(ap.getColuna(), ap);
			}
			clicouEmGerarTotalizadores = true;
			colunaQueEstaSendoDetalhada = -1;
			
			tipos = new SelectItem[]{
					new SelectItem("texto","Texto (Abcd123)"),
					new SelectItem("monetario","Moeda(R$00,00)"),
					new SelectItem("dataInicio", "Data(dd/mm/yyyy)"),
					new SelectItem("dataTime", "Data Time(dd/mm/yyyy hh:mm:ss)"),
					new SelectItem("time","Tempo(hh:mm:ss)"),
					new SelectItem("porcentagem","Porcentagem (0%)"),
					new SelectItem("porcentagemUmaCasa","Porcentagem (0,0%)"),
					new SelectItem("porcentagemDuasCasas", "Porcentagem (0,00%)"),
					new SelectItem("decimalUmaCasa","Decimal (0,0)"),
					new SelectItem("decimalDuasCasas","Decimal (0,00)")
			};
			
			refreshPage();
	}	
	
	public void adicionaOperacao(int coluna){
		colunaQueEstaSendoDetalhada = coluna;
		AtributosDaPesquisa adp = mapaAtributosPesquisa.get(coluna);
		String operacao = adp.getOperacao();
		String operacaoValidadaStr = ValidadorDeOperacoes.validar(operacao, colunasArr);
		if(StringUtils.isNotBlank(operacaoValidadaStr)){
			adp.setOperacao("");
			mapaAtributosPesquisa.put(coluna, adp);
			Messages.addGlobalError("O termo: '" + operacaoValidadaStr + "' não foi reconhecido. A operacão não foi adicionada" );
		}
		else{
			Messages.addGlobalInfo("Operação adicionada com sucesso");
		}
	}
	
	public void adicionaClausula(int coluna){
		colunaQueEstaSendoDetalhada = coluna;
		Messages.addGlobalInfo("Claúsula adicionada com sucesso");
	}
	
	public void adicionaAlias(int coluna){
		colunaQueEstaSendoDetalhada = coluna;
		AtributosDaPesquisa adp = mapaAtributosPesquisa.get(coluna);
		String dummyAlias = adp.getDummyAlias();
		if(StringUtils.isBlank(dummyAlias)){
			adp.setDummyAlias(adp.getAlias());
			mapaAtributosPesquisa.put(coluna, adp);
			Messages.addGlobalError("O alias não pode ser vazio");
		}else{
			Messages.addGlobalInfo("Alias adicionado com sucesso");
		}
	}
		
	public void adicionaTipo(int coluna){
		colunaQueEstaSendoDetalhada = coluna;
		refreshPage();
	}
		
	public void deveMostrarColuna(int coluna){
		colunaQueEstaSendoDetalhada = coluna;
	}
	
	public void onDateSelect(SelectEvent event) throws ParseException {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
        int coluna = Integer.parseInt(params.get("coluna"));
        int calendarioIndex = Integer.parseInt(params.get("calendarioIndex"));
        
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String data = format.format(event.getObject());
        data = "'"+data+"'";
        adicionaClausulaData(coluna, data, calendarioIndex);
    }
	
	private void adicionaClausulaData(int coluna, String data, int calendarioIndex) throws ParseException{
			AtributosDaPesquisa ap = mapaAtributosPesquisa.get(coluna);
			ap.setDataStr(data, calendarioIndex);
			ap.setaClausulaDeDataStr();
			mapaAtributosPesquisa.put(coluna, ap);
			Messages.addGlobalInfo("Claúsula adicionada com sucesso");
	}
		
	public  void geraRelatorio(){
			try {
				PdfUtil.criaPdf(conexaoSelecionada, nome, descricao, query, mapaAtributosPesquisa );
				Messages.addGlobalInfo("Relatório gerado com sucesso");
			} catch (SQLException e) {
				Messages.addGlobalError("Erro ao tentar gerar o relatorio \n" + e.getMessage());
				e.printStackTrace();
			} catch (ParseException e) {
				Messages.addGlobalError("Erro ao tentar gerar o relatorio \n" + e.getMessage());
				e.printStackTrace();
			} catch (ParametrosOperacaoMatematicaErrada e) {
				Messages.addGlobalError("Erro ao tentar gerar o relatorio \n" + e.getMessage());
				e.printStackTrace();
			}
	}
	
	public void salvaRelatorio(){
		try {
			Relatorio relatorio = new Relatorio(subGrupoRelatorioSelecionado, conexaoSelecionada, nome, descricao, query);
			RelatorioDAO relatorioDAO = new RelatorioDAO();
			relatorioDAO.salvar(relatorio);
			
			AtributosDaPesquisaDAO atributosDaPesquisaDAO = new AtributosDaPesquisaDAO();
			for (Map.Entry<Integer,AtributosDaPesquisa> entry : mapaAtributosPesquisa.entrySet()){
				AtributosDaPesquisa ap = entry.getValue();
				ap.setRelatorio(relatorio);
				atributosDaPesquisaDAO.salvar(ap);
			}
			Messages.addGlobalInfo("Relatório salvo com sucesso");
			
		} catch (Exception e) {
			Messages.addGlobalError("Erro ao tentar salvar o relatorio " + "\n"+ e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void excluiRelatorio(Long codigo){
		try{
			RelatorioDAO relatorioDAO = new RelatorioDAO();
			Relatorio relatorio = relatorioDAO.buscar(codigo);
			relatorioDAO.excluir(relatorio);
			Messages.addGlobalInfo("Relatório excluido com sucesso");
		}catch(Exception e){
			Messages.addGlobalError("Erro ao tentar excluir o relatorio "+ "\n"+ e.getMessage());
		}
	}
			
	public Map<Integer, AtributosDaPesquisa> getMapaAtributosPesquisa() {
		return mapaAtributosPesquisa;
	}

	public void setMapaAtributosPesquisa(
			Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) {
		this.mapaAtributosPesquisa = mapaAtributosPesquisa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String[] getColunasArr() {
		return colunasArr;
	}

	public void setColunasArr(String[] colunasArr) {
		this.colunasArr = colunasArr;
	}

	public boolean isClicouEmGerarTotalizadores() {
		return clicouEmGerarTotalizadores;
	}
	
	public void setClicouEmGerarTotalizadores(boolean clicouEmGerarTotalizadores) {
		this.clicouEmGerarTotalizadores = clicouEmGerarTotalizadores;
	}
	
	public List<GrupoRelatorio> getListaGrupoRelatorios() {
		return listaGrupoRelatorios;
	}

	public void setListaGrupoRelatorios(List<GrupoRelatorio> listaGrupoRelatorios) {
		this.listaGrupoRelatorios = listaGrupoRelatorios;
	}
	
	public List<SubGrupoRelatorio> getListaSubGrupoRelatorios() {
		return listaSubGrupoRelatorios;
	}

	public void setListaSubGrupoRelatorios(List<SubGrupoRelatorio> listaSubGrupoRelatorios) {
		this.listaSubGrupoRelatorios = listaSubGrupoRelatorios;
	}

	public List<Conexao> getListaConexoes() {
		return listaConexoes;
	}

	public void setListaConexoes(List<Conexao> listaConexoes) {
		this.listaConexoes = listaConexoes;
	}
	
	public GrupoRelatorio getGrupoRelatorioSelecionado() {
		return grupoRelatorioSelecionado;
	}

	public void setGrupoRelatorioSelecionado(
			GrupoRelatorio grupoRelatorioSelecionado) {
		this.grupoRelatorioSelecionado = grupoRelatorioSelecionado;
	}
	
	public SubGrupoRelatorio getSubGrupoRelatorioSelecionado() {
		return subGrupoRelatorioSelecionado;
	}

	public void setSubGrupoRelatorioSelecionado(SubGrupoRelatorio subGrupoRelatorioSelecionado) {
		this.subGrupoRelatorioSelecionado = subGrupoRelatorioSelecionado;
	}

	public Conexao getConexaoSelecionada() {
		return conexaoSelecionada;
	}

	public void setConexaoSelecionada(Conexao conexaoSelecionada) {
		this.conexaoSelecionada = conexaoSelecionada;
	}
			
	public Relatorio getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		this.relatorio = relatorio;
	}

	public SelectItem[] getTipos() {
		return tipos;
	}

	public void setTipos(SelectItem[] tipos) {
		this.tipos = tipos;
	}

	public int getColunaQueEstaSendoDetalhada() {
		return colunaQueEstaSendoDetalhada;
	}

	public void setColunaQueEstaSendoDetalhada(int colunaQueEstaSendoDetalhada) {
		this.colunaQueEstaSendoDetalhada = colunaQueEstaSendoDetalhada;
	}

	public void refreshPage(){
		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
			ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
}

