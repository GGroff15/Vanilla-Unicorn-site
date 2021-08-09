package com.example.securingweb.controler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import com.example.securingweb.controler.impl.LoginController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LoginControllerTest {
	
	@Autowired
	LoginController controler;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;

	@Test
	void testCarregarPagina() {
		assertNotEquals(null, controler);
		assertEquals("login", controler.carregarPagina(null));
	}
	
	@Test
	void testLogin() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/login", String.class);
		assertNotEquals(null, response);
	}

}
