package com.example.securingweb.model.service.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.service.dao.Dao;

@SpringBootTest
class ConsumoDaoTest {

	@Test
	@WithMockUser("AndreVanilla")
	void testGetIntervaloDatasVO() {
		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();

		IntervaloDatasVO datasVO = new IntervaloDatasVO();
		datasVO.setDataInicial(1623523734990L);
		datasVO.setDataFinal(1623523734994L);

		List<ConsumoVO> consumo = dao.get(datasVO);
		assertNotEquals(0, consumo.size());
		assertNotEquals(null, consumo.get(0));
	}

	@Test
	@WithMockUser("AndreVanilla")
	void testGetAll() {
		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();
		List<ConsumoVO> retorno = dao.getAll();
		assertNotEquals(0, retorno.size());
	}

	@Test
	@WithMockUser("AndreVanilla")
	void testSave() {
		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();

		ConsumoVO consumoVO = new ConsumoVO();
		consumoVO.setData(Calendar.getInstance().getTimeInMillis());
		consumoVO.setDia(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		consumoVO.setMes(Calendar.getInstance().get(Calendar.MONTH));
		consumoVO.setAno(Calendar.getInstance().get(Calendar.YEAR));
		consumoVO.setTempoUso(15);

		consumoVO.setAgua(200D);
		consumoVO.setEnergia(2000D);

		ConsumoVO resultado = dao.save(consumoVO);
		assertNotEquals(null, resultado.get_id());
	}
}
