package br.com.telesul.core.util;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.shiro.authc.SimpleAuthenticationInfo;


public class RecuperaSenha {
	
	private String mailSMTPServer;
	private String mailSMTPServerPort;
	private String mailSenha;
	
	public void sendMail(String from, String to, String subject, String message){
		
		Properties props = new Properties();
		
		mailSMTPServer="smtp.googlemail.com";
		mailSMTPServerPort="465";
		mailSenha="senha";
		
		
		props.put("mail.transport.protocol", "smtp"); //define e protocolo de envio
		props.put("mail.smtp.starttls.enable", "true"); 
		props.put("mail.smtp.host", mailSMTPServer); // server SMTP do gmail
		props.put("mail.smtp.auth", true);   // ativa autenticação
		props.put("mail.smtp.user", from);  //conta que está enviando o email (tem de ser GMAIL!)
		props.put("mail.debug", "true"); 
		props.put("mail.smtp.port", mailSMTPServerPort); //porta
		props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket 
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.socketFactory.fallback", "false");

		
		SimpleAuth auth = null;
		auth = new SimpleAuth (from, mailSenha);
		
		Session session = Session.getDefaultInstance(props, auth);
		session.setDebug(true);
		
		Message msg = new MimeMessage(session);
		
		try{
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			msg.setFrom(new InternetAddress(from));
			msg.setSubject(subject);
			msg.setContent(message, "text/area");
			
			
		} catch(Exception ex){
			System.out.println("Erro mensagem");
			
		}
		
		
		Transport tr;
		try{
			
			tr = session.getTransport("smtp");
			
			tr.connect(mailSMTPServer, from, mailSenha);
			msg.saveChanges();
			tr.sendMessage(msg, msg.getAllRecipients());
			tr.close();
			
		} catch (Exception e){
			System.out.println("Erro mensagem");
		}
			
	}
	

}

	class SimpleAuth extends Authenticator{
		public String username = null;
		public String password = null;
		
		public SimpleAuth(String user, String pwd){
			username = user;
			password = pwd;
		}
		
		@Override
		protected PasswordAuthentication getPasswordAuthentication(){
			return new PasswordAuthentication (username, password);
		}
		
	}




