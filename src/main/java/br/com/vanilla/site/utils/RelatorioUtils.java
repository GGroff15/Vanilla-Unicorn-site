package br.com.vanilla.site.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.RelatorioVO;

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
			relatorioVO.setEnergia(item.getEnergia() / 24000);
			relatorioVO.setTempoUso(item.getTempoUso());
			if (meta != null) {
				relatorioVO.setTempoMeta(meta); // Obter esse valor dinamicamente com base no usuario logado
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			relatorioVO.setData(dateFormat.format(calendar.getTime()));

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

}
