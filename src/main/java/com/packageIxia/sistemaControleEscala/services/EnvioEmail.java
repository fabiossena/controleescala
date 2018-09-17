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

public class EnvioEmail extends Thread  {
	
    private String email;
	private String matricula;
	private String url;
    
    public EnvioEmail(String email, String matricula, String url) {
	    this.email = email;
	    this.matricula = matricula;
	    this.url = url;
    }
    
    @Override
    public void run() {
    	Properties props = new Properties();
          props.put("mail.smtp.host", "smtp.gmail.com");
          props.put("mail.smtp.socketFactory.port", "465");
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.auth", "true");
          props.put("mail.smtp.port", "465");

          Session session = Session.getDefaultInstance(props,
                      new javax.mail.Authenticator() {
                           protected PasswordAuthentication getPasswordAuthentication() 
                           {
                                 return new PasswordAuthentication("fss.naoresponda@gmail.com", "1234.abcd");
                           }
                      });

          session.setDebug(true);

          try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("fss.naoresponda@gmail.com")); //Remetente

                Address[] toUser = InternetAddress //Destinatário(s)
                           .parse(email);  

                message.setRecipients(Message.RecipientType.TO, toUser);
                //message.setHeader("Content-Type", "text/html");
                message.setSubject("Convite Ixia");//Assunto
                message.setContent("Olá, acabamos de te enviar um convite de acesso ao sistema de escala Ixia."
        				+ "<br>"
                		+ " Faça seu cadastro no site <a href='" + this.url + "cadastroinicial'>" + this.url + "cadastroinicial</a> usando a matricula " + this.matricula + " e o seu e-mail (" + email + ")."
                		+ "<br>"
                		+ "Após efetue o login neste link <a href='" + this.url + "login'>" + this.url + "login</a>"
                		+ "<br><br>"
                		+ "Atenciosamente administração", "text/html");
                
                /**Método para enviar a mensagem criada*/
                Transport.send(message);

                System.out.println("Enviado");

           } catch (MessagingException e) {
                throw new RuntimeException(e);
          }
    }

}
