package br.com.telesul.reporting.exception;

public class SintaxeQueryErrada extends Exception{
	
	public SintaxeQueryErrada() {
		super("Verfique se sua query possui os elementos 'SELECT' e 'FROM'");
	}

}
