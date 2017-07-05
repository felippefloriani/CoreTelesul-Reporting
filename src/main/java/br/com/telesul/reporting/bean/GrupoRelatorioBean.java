package br.com.telesul.reporting.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.telesul.core.util.FacesUtil;
import br.com.telesul.reporting.dao.GrupoRelatorioDAO;
import br.com.telesul.reporting.model.GrupoRelatorio;

@ManagedBean
@ViewScoped
public class GrupoRelatorioBean implements Serializable{
	
	private String nome;
	private List<GrupoRelatorio> listaGrupoRelatorios;
		
	public void salvar() {
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			GrupoRelatorio grupoRelatorio = new GrupoRelatorio(nome);
			grupoRelatorioDAO.salvar(grupoRelatorio);
			FacesUtil.adcionarMsgInfo("Grupo de Relatórios salvo com sucesso!");
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar salvar o Grupo de Relatórios!");
			System.out.println(ex);

		}
	}
	
	@PostConstruct
	public void listar() {
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			listaGrupoRelatorios = grupoRelatorioDAO.listar();

		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar listar os Grupos de Relatórios!");

		}
	}
	
	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public List<GrupoRelatorio> getListaGrupoRelatorios() {
		return listaGrupoRelatorios;
	}

	public void setListaGrupoRelatorios(List<GrupoRelatorio> listaGrupoRelatorios) {
		this.listaGrupoRelatorios = listaGrupoRelatorios;
	}
	
	
/*
	public void carregarCadastro() {
		try {		
			if (codigo != null) {
				GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
				grupoRelatorio = grupoRelatorioDAO.buscar(codigo);
			}
			else {
				grupoRelatorio = new GrupoRelatorio();
			}
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar obter os dados do Grupo de Relatório!");
					
		}
	}
	
	public void excluir() {
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			grupoRelatorioDAO.excluir(grupoRelatorio);
			FacesUtil.adcionarMsgInfo("Grupo de Relatórios excluído com sucesso!");
		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar excluir o Grupo de Relatórios! </br> Verfique se não há algum SubGrupo a ele vinculado!");
		}
	}

	
	
	public void editar() {
		try {
			GrupoRelatorioDAO grupoRelatorioDAO = new GrupoRelatorioDAO();
			grupoRelatorioDAO.editar(grupoRelatorio);

			FacesUtil.adcionarMsgInfo("Grupo Relatorio editado com sucesso!");

		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar editar o Grupo de Relatório!");

		}
	}

	public void novo() {
		grupoRelatorio = new GrupoRelatorio();

	}
	
	

	public GrupoRelatorio getGrupoRelatorioCadastro() {
		return grupoRelatorio;
	}

	public void setGrupoRelatorioCadastro(GrupoRelatorio grupoRelatorioCadastro) {
		this.grupoRelatorio = grupoRelatorioCadastro;
	}
	*/

}
