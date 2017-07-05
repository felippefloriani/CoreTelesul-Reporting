package br.com.telesul.core.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.telesul.core.util.HibernateUtil;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.model.SubGrupoRelatorio;

public class ConexaoLocalDAO {
	

	public void salvar(Conexao conexao){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		
		try {
			transacao = sessao.beginTransaction();
			sessao.save(conexao);
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
	
	
	public List<Conexao> listar(){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<Conexao> conexoes = null;

		try {
			Query query = sessao.createQuery("from Conexao"); 
			conexoes = query.list();
		} catch (RuntimeException ex) {

			throw ex;
			
		} finally {
			
			sessao.close();

		}
		return conexoes;
		
	}
	
	
	public Conexao buscarPorCodigo(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<Conexao> listaSubGrupoRelatorio = null;

		try {
			Query query = sessao.createQuery("FROM Conexao WHERE codigo = :code ");
			query.setParameter("code", codigo);
			listaSubGrupoRelatorio = query.list();
			return listaSubGrupoRelatorio.get(0);
			
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();
		}
		
	}
	
	public void excluir(Conexao conexao){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.delete(conexao);
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
	
	public void editar(Conexao conexao){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();
			sessao.update(conexao);
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
	

}
