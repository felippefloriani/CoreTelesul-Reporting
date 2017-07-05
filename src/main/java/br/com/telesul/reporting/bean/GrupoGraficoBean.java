package br.com.telesul.reporting.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.telesul.core.util.FacesUtil;
import br.com.telesul.reporting.dao.GrupoGraficoDAO;
import br.com.telesul.reporting.model.GrupoGrafico;


@ManagedBean
@ViewScoped
public class GrupoGraficoBean implements Serializable {

	
	private String nome;
	private List<GrupoGrafico> listaGrupoGraficos;
	
	
	public void salvar(){
		try {
			GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
			GrupoGrafico grupoGrafico = new GrupoGrafico(nome);
			grupoGraficoDAO.salvar(grupoGrafico);
			FacesUtil.adcionarMsgInfo("Grupo de Graficos salvo com sucesso!");
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar salvar o Grupo de Gráficos!");
			System.out.println(ex);

		}
		
	}
	
	@PostConstruct
	public void listar(){
		try {
			GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
			listaGrupoGraficos = grupoGraficoDAO.listar();

		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar listar os Grupos de Graficos!");

		}
		
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<GrupoGrafico> getListaGrupoGraficos() {
		return listaGrupoGraficos;
	}

	public void setListaGrupoGraficos(List<GrupoGrafico> listaGrupoGraficos) {
		this.listaGrupoGraficos = listaGrupoGraficos;
	}
	
	
	
	
	/*
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
				grupoGrafico = grupoGraficoDAO.buscar(codigo);
			}
			else {
				grupoGrafico = new GrupoGrafico();
			}
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar obter os dados do Grupo de Grafico!");
					
		}
	}
	
	public void excluir() {
		try {
			GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
			grupoGraficoDAO.excluir(grupoGRafico);
			FacesUtil.adcionarMsgInfo("Grupo de Gráficos excluído com sucesso!");
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar excluir o Grupo de Gráficos! </br> Verfique se não há algum SubGrupo a ele vinculado!");
		}
	}

	
	
	public void editar() {
		try {
			GrupoGraficoDAO grupoGraficoDAO = new GrupoGraficoDAO();
			grupoGraficoDAO.editar(grupoGrafico);

			FacesUtil.adcionarMsgInfo("Grupo Grafico editado com sucesso!");

		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar editar o Grupo de Grafico!");

		}
	}

	public void novo() {
		grupoGrafico = new GrupoGrafico();

	}
	
	

	public GrupoGrafico getGrupoGraficoCadastro() {
		return grupoGrafico;
	}

	public void setGrupoGraficoCadastro(GrupoGrafico grupoGraficoCadastro) {
		this.grupoGrafico = grupoGraficoCadastro;
	}
	*/
	
	
}
