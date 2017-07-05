package br.com.telesul.reporting.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import br.com.telesul.reporting.conf.infra.ConexaoInfra;
import br.com.telesul.reporting.model.AtributosDaPesquisa;
import br.com.telesul.reporting.model.Conexao;
import br.com.telesul.reporting.util.FabricaDeFiltros;

public class RelatorioQueryDao {
	
	private Connection connection;
	private List<String[]> resultadoDaConsulta;
	
	
	public RelatorioQueryDao(Conexao conexao, String reportQuery, Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) throws SQLException {
		ConexaoInfra conecta = new ConexaoInfra(conexao);
		this.connection= conecta.getConn();
		String query = FabricaDeFiltros.constroiQuery(reportQuery, mapaAtributosPesquisa);
		resultadoDaConsulta = rodaReportQuery(query, mapaAtributosPesquisa);
	}
	
	private List<String[]> rodaReportQuery(String reportQuery,  Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) throws SQLException{
		List<String[]> lista = new ArrayList<String[]>();
		String[] arr = null;;
		
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(reportQuery);
		
		while (rs.next()) {
			arr = new String[mapaAtributosPesquisa.size()];
			for(int i =0; i< arr.length; i++){
				AtributosDaPesquisa ap = mapaAtributosPesquisa.get(i);
				arr[i] = rs.getString(ap.getAlias());
			}
			lista.add(arr);
		}
		rs.close();
		statement.close();
		return lista;
	}

	public List<String[]> getResultadoDaConsulta() {
		return resultadoDaConsulta;
	}

	public void setResultadoDaConsulta(List<String[]> resultadoDaConsulta) {
		this.resultadoDaConsulta = resultadoDaConsulta;
	}

	

}
