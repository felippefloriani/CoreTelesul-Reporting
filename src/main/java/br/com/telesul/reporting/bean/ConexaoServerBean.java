package br.com.telesul.reporting.bean;

import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import br.com.telesul.core.bean.ConexaoLocalBean;
import br.com.telesul.reporting.conf.definicao.DBType;
import br.com.telesul.reporting.conf.infra.ConexaoInfra;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.util.DefinidorBanco;

@ManagedBean
@SessionScoped
public class ConexaoServerBean {
	
	@ManagedProperty(value="#{conexao}")
	private Conexao conexao;
	
	
	
	public void testar(){
		boolean conectado = isConectado(this.conexao.getBanco(), this.conexao.getIp(), this.conexao.getPorta(), this.conexao.getSchema(), this.conexao.getUsuario(), this.conexao.getSenha());
		if(conectado){
			System.out.println("Conectou no " + this.conexao.getBanco());
		}else{
			System.out.println("Erro ao conectar no " + this.conexao.getBanco());
		}
	}
	
	public void salvar(){
		try{
			ConexaoLocalBean conexaoLocalBean = new ConexaoLocalBean();
			conexaoLocalBean.salvar(conexao);
			System.out.println("Conexao Salva");
			Messages.addGlobalInfo("Conexao salva com sucesso!");
		}catch(RuntimeException erro){
			Messages.addGlobalError("Erro ao salvar a conexão!");
			erro.printStackTrace();
		}
	}
	
	private boolean isConectado(String bancoStr, String ip, String porta, String schema, String user, String senha ){
		try {
			DBType banco = DefinidorBanco.getBancoEnum(bancoStr);
			ConexaoInfra conecta = new ConexaoInfra(banco, ip, porta, schema, user, senha);
			conecta.getConn();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	public Conexao getConexao() {
		return conexao;
	}
	public void setConexao(Conexao conexao) {
		this.conexao = conexao;
	}
	
	
	
	
	
	
	

}
