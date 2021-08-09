package com.example.securingweb.model.config.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.securingweb.model.config.event.OnRegistrationCompleteEvent;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.cadastro.Cadastro;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	Cadastro cadastro;
	
	@Autowired
	MessageSource messageSource;
	
	@Autowired
	JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {


		
	}
	
	private void confirmarCadastro(OnRegistrationCompleteEvent event) {
		UsuarioVO usuarioVO = event.getUsuarioVO();
		String token = UUID.randomUUID().toString();
		cadastro.createVerificationTokenForUser(usuarioVO, token);
		
		String recipientAddress = usuarioVO.getEmail();
		String assunto = "Confirmar Email";
		String urlConfirmacao = event.getUrl() + "/confirmar.html?token=" + token;
		
		String mensagem = messageSource.getMessage("message.regSucc", null, event.getLocal());
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(assunto);
		email.setText(mensagem + "\r\n" + "http://localhost:8080" + urlConfirmacao);
		mailSender.send(email);
	}

}
