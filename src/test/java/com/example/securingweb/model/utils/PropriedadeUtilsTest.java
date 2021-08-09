package com.example.securingweb.model.utils;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class PropriedadeUtilsTest {

	@Test
	void testGet() {
		String valor = PropriedadeUtils.get("mail.senha");
		assertNotEquals(null, valor);
	}

}
