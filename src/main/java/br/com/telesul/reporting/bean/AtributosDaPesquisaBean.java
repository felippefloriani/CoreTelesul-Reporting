package br.com.telesul.reporting.bean;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;

import br.com.telesul.reporting.dao.AtributosDaPesquisaDAO;
import br.com.telesul.reporting.dao.RelatorioDAO;
import br.com.telesul.reporting.exception.SintaxeParametrosBetweenErrada;
import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.model.Relatorio;
import br.com.telesul.reporting.util.FormatadorDeStrings;
import br.com.telesul.reporting.util.PdfUtil;

@ManagedBean
@SessionScoped
public class AtributosDaPesquisaBean {
	
	private List<AtributosDaPesquisa> atributosDaPesquisaList;
	private List<Relatorio> relatorioList;
	private Relatorio relatorio;
	private boolean ehPraMostrarDialog;
	private int colunaAnalisada = -1;

		
	 	@PostConstruct
	    public void init() {
	 		RelatorioDAO relatorioDAO = new RelatorioDAO();
			relatorioList = relatorioDAO.listar();
	 	}
	 	
	 	public void carregaAtributosDaPesquisa(){
	 		AtributosDaPesquisaDAO adpDao = new AtributosDaPesquisaDAO();
	 		atributosDaPesquisaList = adpDao.buscaPorRelatorio(relatorio.getCodigo());
	 		atributosDaPesquisaList.forEach(adp -> adp.setaClausulaDeData());
	 		Collections.sort(atributosDaPesquisaList);
	 		ehPraMostrarDialog = true;
	 	}
	 	
	 	public void atualizarRelatorio(AtributosDaPesquisa adp){
	 		AtributosDaPesquisaDAO adpDao = new AtributosDaPesquisaDAO();
	 		adpDao.editar(adp);
	 		refreshPage();
	 	}
	 	
	 	public void atualizarRelatorio(int coluna){
	 		AtributosDaPesquisa adp = atributosDaPesquisaList.get(coluna);
	 		AtributosDaPesquisaDAO adpDao = new AtributosDaPesquisaDAO();
	 		adpDao.editar(adp);
	 		refreshPage();
	 	}
	 	
	 		 	
	 	public void onDateSelect(SelectEvent event) throws ParseException {
	 		//irá pegar os parametros coluna e calendarioIndex e a data da pagina
	 		 FacesContext fc = FacesContext.getCurrentInstance();
	 		 Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	 		 int coluna  = Integer.parseInt(params.get("coluna"));
	 		 int calendarioIndex = Integer.parseInt(params.get("calendarioIndex"));
	 		 	 		 
	 		 SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	         String data = format.format(event.getObject());
	         data = "'"+data+"'";
	         
	         // irá achar o referente objeto na lista e irá setar os novos atributos
	         AtributosDaPesquisa adp = atributosDaPesquisaList.get(coluna);
	         adp.setDataStr(data, calendarioIndex);
		 	 adp.setaClausulaDeDataStr();
		 	 
		 	 atributosDaPesquisaList.set(coluna, adp);
	 	}
	 	
	 	public void geraRelatorio(){
			try {
				PdfUtil.criaPdf(relatorio, atributosDaPesquisaList);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public void refreshPage(){
			try {
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	 	

	 	public List<Relatorio> getRelatorioList() {
			return relatorioList;
		}

	 	public void setRelatorioList(List<Relatorio> relatorioList) {
			this.relatorioList = relatorioList;
		}
	 	
	 	public List<AtributosDaPesquisa> getAtributosDaPesquisaList() {
	 		return atributosDaPesquisaList;
	 	}

	 	public void setAtributosDaPesquisaList(
			List<AtributosDaPesquisa> atributosDaPesquisaList) {
	 		this.atributosDaPesquisaList = atributosDaPesquisaList;
	 	}

		public Relatorio getRelatorio() {
			return relatorio;
		}

		public void setRelatorio(Relatorio relatorio) {
			this.relatorio = relatorio;
		}

		public boolean isEhPraMostrarDialog() {
			return ehPraMostrarDialog;
		}

		public void setEhPraMostrarDialog(boolean ehPraMostrarDialog) {
			this.ehPraMostrarDialog = ehPraMostrarDialog;
		}

		public int getColunaAnalisada() {
			return colunaAnalisada;
		}

		public void setColunaAnalisada(int colunaAnalisada) {
			this.colunaAnalisada = colunaAnalisada;
		}

		public void handleToggle(int colunaAnalisada) {
			this.colunaAnalisada = colunaAnalisada;
	      }

		
	 	

	 	
	
	
	
	
	

}
