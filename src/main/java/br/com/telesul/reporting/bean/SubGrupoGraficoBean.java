package br.com.telesul.reporting.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.telesul.reporting.dao.GrupoGraficoDAO;
import br.com.telesul.reporting.dao.GrupoRelatorioDAO;
import br.com.telesul.reporting.dao.SubGrupoGraficoDAO;
import br.com.telesul.reporting.dao.SubGrupoRelatorioDAO;
import br.com.telesul.reporting.model.GrupoGrafico;
import br.com.telesul.reporting.model.SubGrupoGrafico;
import br.com.telesul.reporting.model.SubGrupoRelatorio;



@ManagedBean
@ViewScoped
public class SubGrupoGraficoBean implements Serializable {
	
	private String nome;
	private GrupoGrafico grupoGraficoSelecionado;
	private List<SubGrupoGrafico> listaSubGrupoGraficos;
	private List<GrupoGrafico> listaGrupoGraficos;
	
	
	public void salvar(){
		try{
			SubGrupoGraficoDAO subGrupoGraficoDAO = new SubGrupoGraficoDAO();
			SubGrupoGrafico subGrupoGrafico = new SubGrupoGrafico(nome, grupoGraficoSelecionado);
			subGrupoGraficoDAO.salvar(subGrupoGrafico);
			Messages.addGlobalInfo("Subgrupo de Gráfico salvo com sucesso!");
			System.out.println("subgrupo adicionado");
		} catch(RuntimeException ex){
			Messages.addGlobalError("Erro ao tentar salvar um Subgrupo de Gráfico!");
			ex.printStackTrace();
		}
	}
		
	@PostConstruct
	public void listarGrupoESubGrupoGraficoo(){
		try {
			GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
			listaGrupoGraficos = grupoGraficoDAO.listar();
			
			SubGrupoGraficoDAO subGrupoGraficoDAO = new SubGrupoGraficoDAO();
			listaSubGrupoGraficos = subGrupoGraficoDAO.listar();
			
		} catch (RuntimeException ex){
			Messages.addGlobalError("Erro ao listar um Grupo de Gráficos!");
			ex.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoGrafico getGrupoGraficoSelecionado() {
		return grupoGraficoSelecionado;
	}

	public void setGrupoGraficoSelecionado(GrupoGrafico grupoGraficoSelecionado) {
		this.grupoGraficoSelecionado = grupoGraficoSelecionado;
	}

	public List<SubGrupoGrafico> getListaSubGrupoGraficos() {
		return listaSubGrupoGraficos;
	}

	public void setListaSubGrupoGraficos(List<SubGrupoGrafico> listaSubGrupoGraficos) {
		this.listaSubGrupoGraficos = listaSubGrupoGraficos;
	}

	public List<GrupoGrafico> getListaGrupoGraficos() {
		return listaGrupoGraficos;
	}

	public void setListaGrupoGraficos(List<GrupoGrafico> listaGrupoGraficos) {
		this.listaGrupoGraficos = listaGrupoGraficos;
	}


}
