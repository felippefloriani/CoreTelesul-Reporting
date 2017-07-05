package br.com.telesul.reporting.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.telesul.core.util.HibernateUtil;
import br.com.telesul.reporting.model.AtributosDaPesquisa;

public class AtributosDaPesquisaDAO  extends GenericDAO<AtributosDaPesquisa>{
	
	public List<AtributosDaPesquisa> buscaPorRelatorio(Long codigo){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<AtributosDaPesquisa> listaAtributos = null;

		try {
			Query query = sessao.createQuery("FROM AtributosDaPesquisa WHERE relatorio.codigo = :code ");
			query.setParameter("code", codigo);
			listaAtributos = query.list();
		} catch (RuntimeException ex) {

			throw ex;
			
		} finally {
			
			sessao.close();

		}
		return listaAtributos;
		
	}
	
	
	public AtributosDaPesquisa buscaPorAlias(String alias){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<AtributosDaPesquisa> listaAtributos = null;
		try {
			Query query = sessao.createQuery("FROM AtributosDaPesquisa WHERE alias = :a ");
			query.setParameter("a", alias);
			listaAtributos = query.list();
			return listaAtributos.get(0);
		} catch (RuntimeException ex) {
			throw ex;
		} finally {
			sessao.close();

		}
	}

	
}
