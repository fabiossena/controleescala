package com.packageIxia.sistemaControleEscala.services;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.core.env.Environment;

public class EnvioEmail extends Thread  {
	
    private String emailEnvio;
	private String mensagem;
	private String assunto;
	private Environment environment;
    
    public EnvioEmail(
    		Environment environment,
    		String emailEnvio, 
    		String assunto, 
    		String mensagem) {
    	this.environment = environment;
	    this.emailEnvio = emailEnvio;
	    this.assunto = assunto;
	    this.mensagem = mensagem;
    }
    
    @Override
    public void run() {
    	String email = this.environment.getProperty("ixia.sistema.escala.email");
    	String senha = this.environment.getProperty("ixia.sistema.escala.senha");
    	String smtp = this.environment.getProperty("ixia.sistema.escala.smtp");
    	String port = this.environment.getProperty("ixia.sistema.escala.port");
    	String socket = this.environment.getProperty("ixia.sistema.escala.socket");
    	String auth = this.environment.getProperty("ixia.sistema.escala.auth");

    	Properties props = new Properties();
		  props.put("mail.smtp.host", smtp);
		  props.put("mail.smtp.socketFactory.port", port);
		  props.put("mail.smtp.socketFactory.class", socket);
		  props.put("mail.smtp.auth", auth);
		  props.put("mail.smtp.port", port);
		
		  Session session = Session.getDefaultInstance(props,
		              new javax.mail.Authenticator() {
		                   protected PasswordAuthentication getPasswordAuthentication() 
		                   {
		                         return new PasswordAuthentication(
		                        		 email,
		                        		 senha);
		                   }
		              });
		
		  session.setDebug(true);
		
		  try {
		
		        Message message = new MimeMessage(session);
		        message.setFrom(new InternetAddress(this.emailEnvio));
		
		        Address[] toUser = InternetAddress 
		                   .parse(this.emailEnvio);  
		
		        message.setRecipients(Message.RecipientType.TO, toUser);
		        message.setSubject(this.assunto);
		        message.setContent(this.mensagem, "text/html");
		
		Transport.send(message);
		
		System.out.println("Enviado");
		
		       } catch (MessagingException e) {
		            throw new RuntimeException(e);
		      }
		}
}
