package br.com.telesul.reporting.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class FormatadorDeStrings {
	
	

	public static String[] geraAlias(String[] colunas){
		String[] alias = new String[colunas.length];
		for(int i=0; i< alias.length; i++){
			if(colunas[i].contains(" AS ")){
				String aux[] = colunas[i].split(" AS ");
				if(aux.length>2){//  CAST(ROUND(SUM(TEMPOLOGADOMAX)/3600,2) AS DECIMAL(5,2)) AS TEMPOLOGADOTOTAL
					alias[i] = aux[aux.length-1];
				}else{
					alias[i] = colunas[i].substring(colunas[i].indexOf(" AS ")+ 4).trim();
				}
			}else{
				alias[i] = colunas[i];
			}
		}
		return alias;
	}
	


	public static String todaysDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public static String formataColuna(String str, String tipo) throws ParseException{
		
		if(tipo.equalsIgnoreCase("monetario")){
			return formataMonetario(str);
		}
		
		if(tipo.equalsIgnoreCase("time")){
			//return formataTempo(str);
		}
		
		if(tipo.equalsIgnoreCase("dataTime")){
			return formataData(str, "dd/MM/yyyy hh:mm:ss");
		}
		if(tipo.startsWith("data")){
			return formataData(str, "dd/MM/yyyy");
		}
		if(tipo.equalsIgnoreCase("porcentagem")){
			return formataPorcentagem(str, 0);
		}
		if(tipo.equalsIgnoreCase("porcentagem")){
			return formataPorcentagem(str, 0);
		}
		if(tipo.equalsIgnoreCase("porcentagemUmaCasa")){
			return formataPorcentagem(str, 1);
		}
		if(tipo.equalsIgnoreCase("porcentagemDuasCasas")){
			return formataPorcentagem(str, 2);
		}
		if(tipo.equalsIgnoreCase("decimalUmaCasa")){
			return formataDecimal(str, "0");
		}
		if(tipo.equalsIgnoreCase("decimalDuasCasas")){
			return formataDecimal(str, "00");
		}
		return str;
	}
	
	private static String formataMonetario(String str){
		double valor = Double.parseDouble(str);
		Locale locale = new Locale("pt", "BR");      
		NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
		str = currencyFormatter.format(valor);
		return str;
	}
	
	private static String formataData(String str, String mascara) throws ParseException{
		if(StringUtils.isNotBlank(str)){
			str = modifyDateLayout(str);
			SimpleDateFormat sdf = new SimpleDateFormat(mascara);
			Date date = sdf.parse(str);
			return sdf.format(date);
		}
		return "";
	}
	
	public static Date toDate(String str){
		try {
			str = str.replaceAll("'", "");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(str);
			return date;
		} catch (Exception e) {
			return new Date();
		}
	}
	
	private static String modifyDateLayout(String str) throws ParseException{
	    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
	    return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}
	

	
	private static String formataPorcentagem(String str, int casas){
		double valor = Double.parseDouble(str);
		NumberFormat defaultFormat = NumberFormat.getPercentInstance();
		defaultFormat.setMinimumFractionDigits(casas);
		str = defaultFormat.format(valor);
		return str;
	}
		
	public static String formataDecimal(String str, String casas){
		double valor = Double.parseDouble(str);
		
		String mascara = "##." + casas;
		
		DecimalFormat f = new DecimalFormat(mascara);
		str =  f.format(valor);
		str = str.replace(".", ",");
		return str;
		
	}
	
	
	
	public static String formataTempo(double value){
		try{
			long totalSecs = Math.round(value);
			long hours = totalSecs / 3600;
			long minutes = (totalSecs % 3600) / 60;
			long seconds = totalSecs % 60;

			return hours+":"+minutes+":"+seconds;
		
		}catch(NumberFormatException e){
		}
		return null;
	}
	
	public  static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)"+regex+"(?!.*?"+regex+")", replacement);
    }
	
	public static  String[] geraDataAPartirDeClausula(String clausula) {
	    int count=0;
	    String[] allMatches = new String[2];
	    Matcher m = Pattern.compile ("\\d{2}-\\d{2}-\\d{4}").matcher(clausula);
	    while (m.find()) {
	        allMatches[count++] = m.group();
	    }
	    return allMatches;
	}
	
	
	
}
