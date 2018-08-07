package com.packageIxia.SistemaControleEscala.Services.Usuario;

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

	public void envioEmail2(String email, String matricula) {    	
//    	SimpleEmail email = new SimpleEmail();
//    	email.("mail.myserver.com"); // o servidor SMTP para envio do e-mail
//    	email.addTo("jdoe@somewhere.org", "John Doe"); //destinatário
//    	email.setFrom("me@apache.org", "Me"); // remetente
//    	email.setSubject("Mensagem de Teste"); // assunto do e-mail
//    	email.setMsg("Teste de Email utilizando commons-email"); //conteudo do e-mail
//    	email.send(); //envia o e-mail
    }
    
    public EnvioEmail(String email, String matricula, String url) {
	    this.email = email;
	    this.matricula = matricula;
	    this.url = url;
    }
    
    @Override
    public void run() {
    	Properties props = new Properties();
          /** Parâmetros de conexão com servidor Gmail */
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

          /** Ativa Debug para sessão */
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

                System.out.println("Feito!!!");

           } catch (MessagingException e) {
                throw new RuntimeException(e);
          }
    }

}
