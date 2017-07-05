package br.com.telesul.core.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.codec.digest.DigestUtils;

import br.com.telesul.core.dao.UsuarioDAO;
import br.com.telesul.core.model.Usuario;
import br.com.telesul.core.util.FacesUtil;

@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	
	private Usuario usuarioLogado;
	
	public String autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuarioLogado.getEmail(), 
					DigestUtils.md5Hex(usuarioLogado.getSenha())
					
					);

			
			if (usuarioLogado == null) {
				FacesUtil.adcionarMsgErro("Erro ao tentar autenticar! Email e/ou senha inválidos!");
				//significa que vai ficar na mesma página 
				return null;
				
			} else {
				FacesUtil.adcionarMsgInfo("Usuário autenticado com sucesso!");
				return "/pages/principal.xhtml?faces-redirect=true";
			}

		} catch (RuntimeException ex) {
			FacesUtil.adcionarMsgErro("Erro ao tentar acessar o sistema!");
			    return null;

		}
	}
    	
	
	public String sair() {
		usuarioLogado = null;
		//navegação por redirect e não por forword
		return "/pages/autenticacao.xhtml?faces-redirect=true";

	}
	
	
	public Usuario getUsuarioLogado() {
		if(usuarioLogado == null){
			usuarioLogado = new Usuario();
		}
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	

}
