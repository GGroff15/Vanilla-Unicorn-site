package com.example.securingweb.model.service.arquivos.impl;

import static com.example.securingweb.model.constants.Constants.ARQUIVO_TIPO_CSV;
import static com.example.securingweb.model.constants.Constants.ARQUIVO_TIPO_PDF;
import static com.example.securingweb.model.constants.Constants.ARQUIVO_TIPO_XLXS;

import com.example.securingweb.model.exception.ExtensaoInvalidaException;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;

public class ArquivoFactory {

	public static AbstractArquivoService create(String dataInicial, String dataFinal, String extensao)
			throws ExtensaoInvalidaException {

		if (extensao.toLowerCase().equals(ARQUIVO_TIPO_PDF)) {
			return new ArquivoPDF(dataInicial, dataFinal);

		} else if (extensao.toLowerCase().equals(ARQUIVO_TIPO_CSV)) {
			return new ArquivoCSV(dataInicial, dataFinal);

		} else if (extensao.toLowerCase().equals(ARQUIVO_TIPO_XLXS)) {
			return new ArquivoExcel(dataInicial, dataFinal);

		}

		throw new ExtensaoInvalidaException();
	}

}
