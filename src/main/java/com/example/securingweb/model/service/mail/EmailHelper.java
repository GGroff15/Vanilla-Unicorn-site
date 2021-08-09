package com.example.securingweb.model.service.mail;

import com.example.securingweb.model.service.arquivos.AbstractArquivoService;

public class EmailHelper {

	private MailService mailService;
	private String destinatario;
	private String corpoEmail;
	private AbstractArquivoService arquivo;

	public EmailHelper(String destinatario, String corpoEmail, AbstractArquivoService arquivo) {
		this.destinatario = destinatario;
		this.corpoEmail = corpoEmail;
		this.arquivo = arquivo;
		this.mailService = new MailService();

	}

	public void criar() {
		mailService.setDestinatarios(destinatario);
		mailService.setCorpoEmail(corpoEmail);
		mailService.setArquivoAnexo(arquivo);
		mailService.criar();
	}

}
