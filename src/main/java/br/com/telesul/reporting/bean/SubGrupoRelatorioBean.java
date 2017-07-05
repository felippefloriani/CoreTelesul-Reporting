package br.com.telesul.reporting.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.telesul.reporting.dao.GrupoRelatorioDAO;
import br.com.telesul.reporting.dao.SubGrupoRelatorioDAO;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.model.GrupoRelatorio;
import br.com.telesul.reporting.model.SubGrupoRelatorio;

@ManagedBean
@ViewScoped
public class SubGrupoRelatorioBean implements Serializable{
	
	private String nome;
	private GrupoRelatorio grupoRelatorioSelecionado;
	private List<SubGrupoRelatorio> listaSubGrupoRelatorios;
	private List<GrupoRelatorio> listaGrupoRelatorios;
	
	
	public void salvar(){
		try{
			SubGrupoRelatorioDAO subGrupoRelatorioDAO = new SubGrupoRelatorioDAO();
			SubGrupoRelatorio subGrupoRelatorio = new SubGrupoRelatorio(nome, grupoRelatorioSelecionado);
			subGrupoRelatorioDAO.salvar(subGrupoRelatorio);
			Messages.addGlobalInfo("Subgrupo de Relatório salvo com sucesso!");
			System.out.println("subgrupo adicionado");
		} catch(RuntimeException ex){
			Messages.addGlobalError("Erro ao tentar salvar um Subgrupo de Relatório!");
			ex.printStackTrace();
		}
	}
		
	@PostConstruct
	public void listarGrupoESubGrupoRelatorio(){
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			listaGrupoRelatorios = grupoRelatorioDAO.listar();
			
			SubGrupoRelatorioDAO subGrupoRelatorioDAO = new SubGrupoRelatorioDAO();
			listaSubGrupoRelatorios = subGrupoRelatorioDAO.listar();
			
		} catch (RuntimeException ex){
			Messages.addGlobalError("Erro ao listar um Grupo de Relatório!");
			ex.printStackTrace();
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoRelatorio getGrupoRelatorioSelecionado() {
		return grupoRelatorioSelecionado;
	}

	public void setGrupoRelatorioSelecionado(
			GrupoRelatorio grupoRelatorioSelecionado) {
		this.grupoRelatorioSelecionado = grupoRelatorioSelecionado;
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
	
	
}
