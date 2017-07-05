package br.com.telesul.reporting.util;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.conf.definicao.DBType;

public class DefinidorBanco {

	public static DBType getBancoEnum(String bancoStr) {
		if (bancoStr.equals("Cache 2009") || bancoStr.equals("Cache 2016")) {
			return DBType.CACHE;
		}
		if (bancoStr.equals("MySQL")) {
			return DBType.MYSQL;
		}
		if (bancoStr.equals("Postgres")) {
			return DBType.POSTGRES;
		}
		if (bancoStr.equals("SQLServer")) {
			return DBType.SQL_SERVER;
		} else {
			return null;
		}
	}

	public static String getStrConexao(DBType banco, String ip, String porta, String schema) {
		switch (banco) {
		case MYSQL:
			return "jdbc:mysql://" + ip + getPorta(porta) + "/" + schema;
		case POSTGRES:
			return "jdbc:postgresql://" + ip + getPorta(porta) + "/" + schema;
		case SQL_SERVER:
			return "jdbc:sqlserver://" + ip + getPorta(porta)
					+ ";databaseName=" + schema;
		case CACHE:
			return "jdbc:Cache://" + ip + getPorta(porta) + "/" + schema;
		default:
			return null;
		}
	}

	private static String getPorta(String porta) {
		if (StringUtils.isNotBlank(porta)) {
			return ":" + porta;
		}
		return "";
	}

}
