package br.com.telesul.reporting.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import br.com.telesul.reporting.model.AtributosDaPesquisa;

public class FabricaDeFiltros {
	
	private static String conteudoDoGroupBy = "";

	public static String constroiQuery(String reportQuery, Map<Integer, AtributosDaPesquisa> mapaAtributosPesquisa) {
		reportQuery = reportQuery.toUpperCase();
		reportQuery = separaAntesEDepoisDoGroupBy(reportQuery);
		reportQuery = trataWhere(reportQuery);
		
		StringBuilder query = new StringBuilder();
		query.append(reportQuery);
		for (Map.Entry<Integer, AtributosDaPesquisa> entry : mapaAtributosPesquisa.entrySet()) {
			AtributosDaPesquisa ap = entry.getValue();
			if(StringUtils.isNotBlank(ap.getClausula())){
				query.append(" AND " + ap.getAlias() + " ");
				query.append(ap.getClausula());
			}
		}
		query.append(conteudoDoGroupBy);
		return query.toString();
	}
	
	private static String trataWhere(String query){
		if(query.contains("WHERE")){
			query = query.replaceAll("WHERE", "WHERE 1=1 AND ");
		}
		query = query + "WHERE 1=1";
		return query;
	}
	
	
	 	
	private static String separaAntesEDepoisDoGroupBy(String query){
		if (query.contains(" GROUP BY ")){
			int index = query.indexOf(" GROUP BY ");
			conteudoDoGroupBy = query.substring(index);
			query = query.substring(0,index);
		}
		return query;
	}
}
