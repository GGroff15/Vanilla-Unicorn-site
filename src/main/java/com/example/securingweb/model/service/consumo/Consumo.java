package com.example.securingweb.model.service.consumo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Component
public class Consumo {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Calendar calendar = Calendar.getInstance();

	public List<ConsumoVO> consultar(IntervaloDatasVO intervaloDatasVO) {

		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();

		return dao.get(intervaloDatasVO);
	}

	public List<Object> converterDadosGraficoConsumo(List<ConsumoVO> consumoPeriodo) {
		List<Object> dadosGrafico = new ArrayList<>();
		dadosGrafico.add(Arrays.asList("Data", "Energia (kW)", "Água (L)"));
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(dateFormat.format(calendar.getTime()), item.getEnergia(),
					item.getAgua());

			dadosGrafico.add(coluna);
		}

		return dadosGrafico;
	}

	public List<Object> converterDadosGraficoUso(List<ConsumoVO> consumoPeriodo, Integer meta) {
		List<Object> dadosGrafico = new ArrayList<>();

		if (meta == null || meta == 0) {

			dadosGrafico.add(Arrays.asList("Data", "Real"));
			for (ConsumoVO item : consumoPeriodo) {
				calendar.setTimeInMillis(item.getData());
				List<Object> coluna = Arrays.asList(dateFormat.format(calendar.getTime()), item.getTempoUso());

				dadosGrafico.add(coluna);
			}

		} else {

			dadosGrafico.add(Arrays.asList("Data", "Real", "Meta"));
			for (ConsumoVO item : consumoPeriodo) {
				calendar.setTimeInMillis(item.getData());
				List<Object> coluna = Arrays.asList(dateFormat.format(calendar.getTime()), item.getTempoUso(), meta);

				dadosGrafico.add(coluna);
			}

		}

		return dadosGrafico;
	}

	public List<RelatorioVO> converterDadosRelatorio(List<ConsumoVO> consumoPeriodo, Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (ConsumoVO item : consumoPeriodo) {
			RelatorioVO relatorioVO = new RelatorioVO();

			calendar.setTimeInMillis(item.getData());

			relatorioVO.setAgua(item.getAgua());
			relatorioVO.setEnergia(item.getEnergia());
			relatorioVO.setTempoUso(item.getTempoUso());
			if (meta != null) {
				relatorioVO.setTempoMeta(meta); // Obter esse valor dinamicamente com base no usuario logado
			}
			relatorioVO.setData(dateFormat.format(calendar.getTime()));

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

}
