package com.example.securingweb.controler.impl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.securingweb.model.helper.RelatorioMensalHelper;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.service.mail.EmailHelper;

@Controller
public class RelatorioController {

	@GetMapping("/relatorioMensal")
	public boolean enviarRelatorioMensal(@RequestParam String username, @RequestParam int mes, @RequestParam int meta,
			@RequestParam String email) {

		email = email.replace("%40", "@");

		RelatorioMensalHelper relatorioHelper = new RelatorioMensalHelper(mes, username, meta);
		AbstractArquivoService relatorio = relatorioHelper.criarRelatorioMensal();
		String corpoEmail = relatorioHelper.criarTextoRelatorio();

		EmailHelper emailHelper = new EmailHelper(email, corpoEmail, relatorio);
		emailHelper.criar();

		return true;
	}

	@GetMapping("/relatorioDiario")
	public void enviarRelatorioDiario() {
		// Metodo ainda não implementado
	}

}
