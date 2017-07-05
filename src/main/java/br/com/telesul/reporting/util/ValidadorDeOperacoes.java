package br.com.telesul.reporting.util;

public class ValidadorDeOperacoes {

	public static String validar(String str, String[] colunasArr) {
		String[] operacoesValidas = { "SUM", "COUNT", "AVG", "MAX", "MIN" };
		str = str.toUpperCase();
		str = tiraOperadores(str);
		str = tiraNomesEspeciais(str, colunasArr);
		str = tiraNomesEspeciais(str, operacoesValidas);
		str = tiraNumeros(str);
		return str;
	}

	private static String tiraOperadores(String str) {
		str = str.replaceAll("\\*", " ");
		str = str.replaceAll("\\/", " ");
		str = str.replaceAll("\\+", " ");
		str = str.replaceAll("\\-", " ");
		str = str.replaceAll("\\(", " ");
		str = str.replaceAll("\\)", " ");
		return str;
	}

	public static String tiraNomesEspeciais(String str, String operacao,String resultado) {
		int inicio = str.indexOf(operacao);
		String aux = str.substring(inicio);
		inicio = 0;
		int fim = aux.indexOf(")") + 1;
		aux = aux.substring(inicio, fim);
		String valor = String.valueOf(resultado);
		str = str.replace(aux, valor);
		return str;
	}

	private static String tiraNomesEspeciais(String str, String[] arr) {
		for (String s : arr) {
			try {
				str = str.replace(s.toUpperCase(), "");
			} catch (NullPointerException e) {
				continue;
			}
		}
		return str;
	}

	private static String tiraNumeros(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				str = str.replace(str.charAt(i), new Character(' '));
			}
		}
		str = str.replaceAll(" ", "");
		return str;
	}

	public static boolean isNumeroOuOperador(String str) {
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (Character.isDigit(c) || isOperador(c)) {
				// do nothing
			} else {
				return false;
			}
		}
		return true;
	}

	private static boolean isOperador(char c) {
		switch (c) {
		case '*':
			return true;
		case '/':
			return true;
		case '+':
			return true;
		case '-':
			return true;
		case '(':
			return true;
		case ')':
			return true;
		case '.':
			return true;
		case ',':
			return true;
		case ':':
			return true;
		}
		return false;
	}

}
