package com.example.securingweb.model.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.RelatorioVO;

public class RelatorioUtils {

	private RelatorioUtils() {
	}

	public static List<RelatorioVO> converterDadosRelatorio(List<ConsumoVO> consumoPeriodo, Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (ConsumoVO item : consumoPeriodo) {
			RelatorioVO relatorioVO = new RelatorioVO();

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(item.getData());

			relatorioVO.setAgua(item.getAgua());
			relatorioVO.setEnergia(item.getEnergia());
			relatorioVO.setTempoUso(item.getTempoUso());
			if (meta != null) {
				relatorioVO.setTempoMeta(meta); // Obter esse valor dinamicamente com base no usuario logado
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			relatorioVO.setData(dateFormat.format(calendar.getTime()));

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

}
