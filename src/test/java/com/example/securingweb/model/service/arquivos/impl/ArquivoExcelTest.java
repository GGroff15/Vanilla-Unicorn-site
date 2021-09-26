package com.example.securingweb.model.service.arquivos.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.DatasPesquisaVO;
import com.example.securingweb.model.exception.ExtensaoInvalidaException;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;

class ArquivoExcelTest {

	@Test
	void testPopularPlanilha() throws ExtensaoInvalidaException {
		DatasPesquisaVO datas = new DatasPesquisaVO();
		datas.setDataInicial("10/12/2021");
		datas.setDataFinal("30/12/2021");

		AbstractArquivoService arquivo = null;
		arquivo = ArquivoServiceFactory.create(datas.getDataInicial(), datas.getDataFinal(), "xlxs");
		List<ConsumoVO> consumoVOs = new ArrayList<ConsumoVO>();

		ConsumoVO item = new ConsumoVO();
		item.setData(116161561651L);
		item.setAgua(12D);
		item.setEnergia(12D);
		item.setTempoUso(10);

		consumoVOs.add(item);
		consumoVOs.add(item);

		arquivo.gerar(consumoVOs, 20);

		assertNotEquals(null, arquivo.getBytes());
		assertEquals("relatorio_consumo_perido_10/12/2021_30/12/2021.xlxs", arquivo.getNomeArquivo());

	}

}
