package br.com.telesul.reporting.exception;

public class ParametrosOperacaoMatematicaErrada extends Exception {
	
	public ParametrosOperacaoMatematicaErrada(){
		super("Impossivel operar esse tipo de dado");
	}

}
