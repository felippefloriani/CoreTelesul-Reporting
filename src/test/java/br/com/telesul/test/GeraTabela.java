package br.com.telesul.test;

import br.com.telesul.core.util.HibernateUtil;

public class GeraTabela {
	
	public static void main(String[] args) {
		HibernateUtil.getFabricaDeSessoes();
		HibernateUtil.getFabricaDeSessoes().close();
		System.out.println("Tabelas criadas");
	}
	
}
