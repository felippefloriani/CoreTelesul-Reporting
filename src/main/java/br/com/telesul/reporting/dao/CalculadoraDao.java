package br.com.telesul.reporting.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.conf.definicao.DBType;
import br.com.telesul.reporting.conf.infra.ConexaoInfra;
import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.util.FormatadorDeStrings;

public class CalculadoraDao {
	
	private Connection connection;
	private Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa;
	
	public CalculadoraDao(Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) throws SQLException, ParseException {
		this.mapaAtributosPesquisa = mapaAtributosPesquisa;
		ConexaoInfra conecta = new ConexaoInfra(DBType.SQL_LITE);
		this.connection= conecta.getConn();
		calcula();
	}
	
	private void calcula() throws SQLException, ParseException{
		Statement statement = connection.createStatement();
		for (Map.Entry<Integer, AtributosDaPesquisa> entry : mapaAtributosPesquisa.entrySet()){
			 AtributosDaPesquisa ap = entry.getValue();
			 if((!ap.getTipo().equals("time"))&&StringUtils.isNotBlank(ap.getResultadoStr())){
				 ResultSet rs = statement.executeQuery("SELECT "+ ap.getResultadoStr());
				 rs.next();
				 ap.setResultado(rs.getDouble(1));
			}
		}
		statement.close();
		connection.close();
	}

	public Map<Integer, AtributosDaPesquisa> getMapaAtributosPesquisa() {
		return mapaAtributosPesquisa;
	}

	public void setMapaAtributosPesquisa(
			Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) {
		this.mapaAtributosPesquisa = mapaAtributosPesquisa;
	}
	
}
