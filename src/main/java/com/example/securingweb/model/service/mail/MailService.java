package com.example.securingweb.model.service.mail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import com.example.securingweb.model.service.arquivos.AbstractArquivoService;

public class MailService {

	private String destinatario;
	private byte[] byteArquivoAnexo;
	private String nomeArquivoAnexo;
	private String corpoConteudoEmail;

	public void criar() {
		Properties props = definirPropriedadesJavaMailer();

		Session session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(System.getenv("mail_senha"), System.getenv("email_endereco"));
			}
		});
		session.setDebug(true);

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(System.getenv("email_endereco")));

			Address[] toUser;
			toUser = InternetAddress.parse(destinatario);
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject("Relatório Mensal de Consumo");

			BodyPart corpoEmail = new MimeBodyPart();
			corpoEmail.setContent(corpoConteudoEmail, "text/html;charset=utf-8");

			MimeBodyPart anexo = new MimeBodyPart();

			DataSource dataSource = new ByteArrayDataSource(byteArquivoAnexo, "application/pdf");
			anexo.setDataHandler(new DataHandler(dataSource));
			anexo.setFileName(nomeArquivoAnexo);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(corpoEmail);
			multipart.addBodyPart(anexo);

			message.setContent(multipart);
			Transport.send(message);

		} catch (MessagingException e) {
		}
	}

	public void setDestinatarios(String destinatario) {
		this.destinatario = destinatario;
	}

	public void setArquivoAnexo(AbstractArquivoService arquivo) {
		this.byteArquivoAnexo = arquivo.getBytes();
		this.nomeArquivoAnexo = arquivo.getNomeArquivo();
	}

	public void setCorpoEmail(String corpoEmail) {
		this.corpoConteudoEmail = corpoEmail;
	}

	private Properties definirPropriedadesJavaMailer() {
		Properties props = new Properties();
		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.ssl.checkserveridentity", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		return props;
	}

}
