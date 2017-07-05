package br.com.telesul.core.util;

import br.com.telesul.core.model.Usuario;

public class Mensagem {
	
	private static Usuario usuario;
	
		
	public static void main(String[] args) {
		RecuperaSenha recuperaSenha = new RecuperaSenha();
        //remetente                    //destinatrio       //assunto             //corpo da mensagem
		recuperaSenha.sendMail("felippefloriani@gmail.com", "lfloriani@telesul.com.br", "Recuperação de Senha!", "Mensagem");

	}
	
	
	
	
	

}
