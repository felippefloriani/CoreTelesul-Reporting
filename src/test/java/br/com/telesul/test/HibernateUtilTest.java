package br.com.telesul.test;

import org.hibernate.Session;

import br.com.telesul.core.util.HibernateUtil;

/*
 * Classe testa fábrica conexoes SGBD PostgreSQL 9.6.3.1
 * @author: Luís Felippe Floriani | Telesul 2017.
 */
public class HibernateUtilTest {
	
	public static void main(String[] args) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		sessao.close();
		HibernateUtil.getFabricaDeSessoes().close();
	}
	
}
