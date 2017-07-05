package br.com.telesul.core.dao;



import br.com.telesul.core.model.Usuario;
import br.com.telesul.core.util.HibernateUtil;

import org.hibernate.Session;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

public class UsuarioDAO {
	
	
	public void salvar(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.save(usuario);
			transacao.commit();

		} catch (RuntimeException ex) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			sessao.close();
		}

	}

	

	@SuppressWarnings("unchecked")
	public List<Usuario> listar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<Usuario> usuarios = null;

		try {
			Query consulta = sessao.getNamedQuery("Usuario.listar");
			usuarios = consulta.list();
		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			sessao.close();
		}
		return usuarios;

	}

	

	public Usuario buscarPorCodigo(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Usuario usuario = null;

		try {
			Query consulta = sessao.getNamedQuery("Usuario.buscarPorCodigo");
			consulta.setLong("codigo", codigo);

			usuario = (Usuario) consulta.uniqueResult();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			sessao.close();
		}
		return usuario;

	}

	
	public void excluir(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(usuario);
			transacao.commit();

		} catch (RuntimeException ex) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			sessao.close();
		}

	}



	public void editar(Usuario usuario) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(usuario);
			transacao.commit();
		} catch (RuntimeException ex) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw ex;
		} finally {
			sessao.close();
		}

	}

	

	public Usuario autenticar(String email, String senha) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Usuario usuario = null;

		try {
			
			Query consulta = sessao.getNamedQuery("Usuario.autenticar");
			consulta.setString("email", email);
			consulta.setString("senha", senha);

			usuario = (Usuario) consulta.uniqueResult();

		} catch (RuntimeException ex) {
			throw ex;

		} finally {
			sessao.close();
		}
		return usuario;

	}
	
	

}
