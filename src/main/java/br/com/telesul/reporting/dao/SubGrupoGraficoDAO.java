package br.com.telesul.reporting.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.telesul.core.util.HibernateUtil;
import br.com.telesul.reporting.model.GrupoGrafico;
import br.com.telesul.reporting.model.SubGrupoGrafico;


public class SubGrupoGraficoDAO extends GenericDAO<SubGrupoGrafico>{

	public List<SubGrupoGrafico> buscaPorGrupoGrafico(GrupoGrafico grupoGrafico){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<SubGrupoGrafico> listaSubGrupoGrafico = null;

		try {
			Query query = sessao.createQuery("FROM SubGrupoGrafico WHERE grupoGrafico.codigo = :code ");
			query.setParameter("code", grupoGrafico.getCodigo());
			listaSubGrupoGrafico = query.list();
		} catch (RuntimeException ex) {

			throw ex;
			
		} finally {
			
			sessao.close();

		}
		return listaSubGrupoGrafico;
		
	}
	
	
	
}
