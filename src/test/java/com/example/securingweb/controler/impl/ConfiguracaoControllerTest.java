package com.example.securingweb.controler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConfiguracaoControllerTest {

	@Autowired
	ConfiguracaoController controller;

	@Test
	void testCarregarPagina() {
		assertEquals("dados_conta", controller.carregarPagina(null));
	}

}
