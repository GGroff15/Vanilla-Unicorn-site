package com.example.securingweb.controler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.mock.web.MockHttpSession;

import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.DatasPesquisaVO;
import com.example.securingweb.model.entity.UsuarioVO;

class ConsultaControllerTest {

	ConsultaController controller = new ConsultaController();

	@Test
	void testCarregarPaginaModelHttpSession() {
	}

	@Test
	void testBuscar() {
	}

	@Test
	void testDownloadRelatorio() {
		DatasPesquisaVO datas = new DatasPesquisaVO();
		datas.setDataInicial("10/12/2021");
		datas.setDataFinal("30/12/2021");

		MockHttpSession httpSession = new MockHttpSession();

		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setMeta(20);

		httpSession.setAttribute(Constants.DADOS_USUARIO_SESSION, usuarioVO);

		List<ConsumoVO> consumoVOs = new ArrayList<ConsumoVO>();

		ConsumoVO item = new ConsumoVO();
		item.setData(116161561651L);
		item.setAgua(12D);
		item.setEnergia(12D);
		item.setTempoUso(10);

		consumoVOs.add(item);
		consumoVOs.add(item);

		controller.consumoPeriodo = consumoVOs;
		controller.setDatas(datas);
		HttpEntity<byte[]> retorno = controller.downloadRelatorio(httpSession);

		assertNotEquals(null, retorno);
		assertEquals(1, retorno.getHeaders().size());
		assertEquals("[attachment;filename=\"relatorio_consumo_perido_10/12/2021_30/12/2021.pdf\"]",
				retorno.getHeaders().get("Content-Disposition").toString());
	}

}
