package com.example.securingweb.model.service.consumo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.utils.DataUtils;

@SpringBootTest
class ConsumoServiceTest {

	@Autowired
	Consumo consumo;

	@Test
	@WithMockUser("AndreVanilla")
	void testConsultar() {

		IntervaloDatasVO intervalo = new IntervaloDatasVO();

		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);

		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		List<ConsumoVO> consumoPeriodo = consumo.consultar(intervalo);

		assertNotEquals(0, consumoPeriodo.size());

	}

	@Test
	@WithMockUser("AndreVanilla")
	void testConverterDadosGrafico() {

		IntervaloDatasVO intervalo = new IntervaloDatasVO();

		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);

		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		List<ConsumoVO> consumoPeriodo = consumo.consultar(intervalo);

		List<Object> dadosGrafico = consumo.converterDadosGraficoConsumo(consumoPeriodo);

		assertNotEquals(0, dadosGrafico.size());

		for (Object object : dadosGrafico) {
			@SuppressWarnings("unchecked")
			List<String> lista = (List<String>) object;
			assertEquals(3, lista.size());
		}
	}

}
