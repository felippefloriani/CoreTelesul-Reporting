package br.com.telesul.core.bean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import br.com.telesul.core.dao.ConexaoLocalDAO;
import br.com.telesul.reporting.model.Conexao;

@ManagedBean
@SessionScoped
public class ConexaoLocalBean {
	
	private List<Conexao> conexoes;
	private Conexao conexaoSelecionada;
		
	public void salvar(Conexao conexao){
		ConexaoLocalDAO conexaoDAO = new ConexaoLocalDAO();
		conexaoDAO.salvar(conexao);
	}
	
	@PostConstruct
	public void listar() {
		try {
			ConexaoLocalDAO conexaoDAO = new ConexaoLocalDAO();
			conexoes = conexaoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao listar os usuários");
			erro.printStackTrace();
		}

	}

	public List<Conexao> getConexoes() {
		return conexoes;
	}

	public void setConexoes(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}

	

	public Conexao getConexaoSelecionada() {
		return conexaoSelecionada;
	}

	public void setConexaoSelecionada(Conexao conexaoSelecionada) {
		this.conexaoSelecionada = conexaoSelecionada;
	}

	public Conexao getConexaoSelecionada(Long id){
		 if (id == null){
	            throw new IllegalArgumentException("no id provided");
		 }
		 for(Conexao conexao: conexoes){
			 if (id.equals(conexao.getCodigo())){
				 return conexao;
			 }
		 }
		 return null;
	}

	

}

