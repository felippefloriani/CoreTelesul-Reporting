package br.com.telesul.test;


import org.junit.Ignore;
import org.junit.Test;

import br.com.telesul.core.dao.UsuarioDAO;
import br.com.telesul.core.model.Usuario;

public class UsuarioDAOtest {
	
	@Test
	public void salvar(){
		
		Usuario usuario = new Usuario();
		
		usuario.setNome("Joao");
		usuario.setEmail("jao@telesul.com.br");
		usuario.setPerfil("Administrador");
		usuario.setSenha("12345");
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
	}
	
	
	
	@Test
	@Ignore
	public void testar(){
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();

		Usuario usuario = usuarioDAO.autenticar("felippefloriani@gmail.com", "12345");
		
		System.out.println("Usuario " + usuario);
		
	}
	
	
	

}
