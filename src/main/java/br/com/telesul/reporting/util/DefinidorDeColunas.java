package br.com.telesul.reporting.util;

import java.util.LinkedHashSet;
import java.util.Set;

public class DefinidorDeColunas {
		
	
			private static String filtraQuery(String query){
				query = delimitaColunas(query);
				query = trocaPontoPonto(query);
				query = query.replace("\n", "").replace("\r", "").replace(", ", ",").replace("@", "");
				query = trocaVirgulaSeForParametroDeSubstring(query);
				return query;
			}
			
			private static String trocaPontoPonto(String query){
				if (query.contains("::")){
					StringBuilder sb = new StringBuilder(query);
					for(int i = query.indexOf("::");i<query.length(); i++ ){
						if(query.charAt(i)==','){
							break;
						}
						sb.setCharAt(i, '@'); //empty char
					}
					return sb.toString();
				}
				return query;
			}
			
			private static String delimitaColunas(String query){
				query = query.toUpperCase();
				String aux = query.substring(7, query.indexOf("FROM")).trim();
				return aux + ",x";
			}
			
			
			
			private static String trocaVirgulaSeForParametroDeSubstring(String str){ //(5,2)
				char previous, next;
				for(int i =1; i< str.length()-1; i++){
					previous = str.charAt(i-1);
					next = str.charAt(i+1);
					if((Character.isDigit(previous)|| previous==')' )&& Character.isDigit(next)){
						str = str.substring(0,i)+"-"+str.substring(i+1);
					}
				}
				return str;
			}
	
			public static String[] separaColunas(String query){
				query = filtraQuery(query);
				Set<String> set = new LinkedHashSet<String>();
				char previous;
				char current;
				char next;
				for(int i=1; i< query.length()-1; i++){
					previous = query.charAt(i-1);
					current = query.charAt(i);
					next = query.charAt(i+1);
					if (current ==','){
						if(charPermitido(previous) && charPermitido(next)){
							int j = i-1;
							previous = query.charAt(j);
							while(charPermitido(previous) ){
								try{
									previous = query.charAt(--j);
								}catch(IndexOutOfBoundsException e){
									break;
								}
							}
							String aux = query.substring(j+1,i).trim();
							set.add(aux);
						}
					}
				}				
				return set.toArray(new String[set.size()]);
			}
			
			private static boolean charPermitido(char c){
				if(Character.isAlphabetic(c)){
					return true;
				}
				if(Character.isDigit(c)){
					return true;
				}
				if(c == '_'){
					return true;
				}
				return false;
			}

}
