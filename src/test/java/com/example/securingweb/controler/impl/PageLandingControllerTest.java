package com.example.securingweb.controler.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.securingweb.controler.impl.PageLandingController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PageLandingControllerTest {
	
	@Autowired
	PageLandingController controler;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	@Test
	void testCarregarPagina() {
		assertNotEquals(null, controler);
		String retorno = controler.carregarPagina(null);
		assertEquals("pageLanding", retorno);
	}
	
	@Test
	void testPageLanding() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/", String.class);
		assertNotEquals(null, response);
	}

}
