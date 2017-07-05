package br.com.telesul.reporting.conf.infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.telesul.reporting.conf.definicao.DBType;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.util.DefinidorBanco;

public class ConexaoInfra {
	
	private Connection conn;
	
	public ConexaoInfra(Conexao conexao) throws SQLException {
		DBType banco = DefinidorBanco.getBancoEnum(conexao.getBanco());
		this.conn = getConnection(banco, conexao.getIp(), conexao.getPorta(), conexao.getSchema(), conexao.getUsuario(), conexao.getSenha());
	}
	
			
	public ConexaoInfra(DBType banco, String ip, String porta, String schema, String user, String senha) throws SQLException {
		this.conn = getConnection(banco, ip, porta, schema, user, senha);
	}
	
	public ConexaoInfra (DBType banco) throws SQLException{
		this.conn = getConnection(banco, "", "", "", "", "");
	}
	
	private Connection getConnection(DBType banco, String ip, String porta, String schema, String user, String senha ) throws SQLException {
		String strConexao = DefinidorBanco.getStrConexao(banco, ip, porta, schema);
		try {
			switch (banco) {
			case MYSQL:
				Class.forName("com.mysql.jdbc.Driver");
				return DriverManager.getConnection(strConexao,user, senha);
			case POSTGRES:
				Class.forName("org.postgresql.Driver");
				return DriverManager.getConnection(strConexao, user , senha);
			case CACHE:
				Class.forName("com.intersys.jdbc.CacheDriver").newInstance();
				return DriverManager.getConnection(strConexao, user, senha);
			case SQL_SERVER:
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				return DriverManager.getConnection(strConexao, user, senha);
			
			case SQL_LITE:
				Class. forName("org.sqlite.JDBC");
				return DriverManager.getConnection("jdbc:sqlite::memory:");
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

	public Connection getConn() {
		return conn;
	}

}
