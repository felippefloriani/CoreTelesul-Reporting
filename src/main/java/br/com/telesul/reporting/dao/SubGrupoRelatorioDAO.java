package br.com.telesul.reporting.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.telesul.core.util.HibernateUtil;
import br.com.telesul.reporting.model.GrupoRelatorio;
import br.com.telesul.reporting.model.SubGrupoRelatorio;

public class SubGrupoRelatorioDAO extends GenericDAO<SubGrupoRelatorio> {
	
	public List<SubGrupoRelatorio> buscaPorGrupoRelatorio(GrupoRelatorio grupoRelatorio){
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		List<SubGrupoRelatorio> listaSubGrupoRelatorio = null;

		try {
			Query query = sessao.createQuery("FROM SubGrupoRelatorio WHERE grupoRelatorio.codigo = :code ");
			query.setParameter("code", grupoRelatorio.getCodigo());
			listaSubGrupoRelatorio = query.list();
		} catch (RuntimeException ex) {

			throw ex;
			
		} finally {
			
			sessao.close();

		}
		return listaSubGrupoRelatorio;
		
	}

}
