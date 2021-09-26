package com.example.securingweb.model.service.arquivos.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.exception.ExtensaoInvalidaException;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;

class ArquivoServiceFactoryTest {

	@Test
	void testCreateArquivoPdf() throws ExtensaoInvalidaException {
		AbstractArquivoService arquivoService = ArquivoServiceFactory.create("0-0-0", "0-0-0",
				Constants.ARQUIVO_TIPO_PDF);
		assertEquals("relatorio_consumo_perido_0-0-0_0-0-0.pdf", arquivoService.getNomeArquivo());
	}

	@Test
	void testCreateArquivoExcel() throws ExtensaoInvalidaException {
		AbstractArquivoService arquivoService = ArquivoServiceFactory.create("0-0-0", "0-0-0",
				Constants.ARQUIVO_TIPO_XLXS);
		assertEquals("relatorio_consumo_perido_0-0-0_0-0-0.xlxs", arquivoService.getNomeArquivo());
	}

	@Test
	void testCreateArquivoCsv() throws ExtensaoInvalidaException {
		AbstractArquivoService arquivoService = ArquivoServiceFactory.create("0-0-0", "0-0-0",
				Constants.ARQUIVO_TIPO_CSV);
		assertEquals("relatorio_consumo_perido_0-0-0_0-0-0.csv", arquivoService.getNomeArquivo());
	}

	@Test
	void testCreateException() {
		assertThrows(ExtensaoInvalidaException.class, () -> ArquivoServiceFactory.create("0-0-0", "0-0-0", "abapg"));
	}

}
