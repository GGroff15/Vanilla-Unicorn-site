package com.example.securingweb.model.validacoes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EmailValidatorTest {

	EmailValidator emailValidator = new EmailValidator();

	@Test
	void testInitialize() {
		assertThrows(UnsupportedOperationException.class, () -> emailValidator.initialize(null));
	}

	@Test
	void testIsValid() {
		boolean valido = emailValidator.isValid("teste@teste.com.br", null);
		assertTrue(valido);
	}

	@Test
	void testNotValid() {
		boolean valido = emailValidator.isValid("teste", null);
		assertFalse(valido);
	}

}
