package br.com.vanilla.site.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.vanilla.site.entity.ConsumoDTO;
import br.com.vanilla.site.entity.LeituraDTO;
import br.com.vanilla.site.entity.RelatorioVO;

public class RelatorioUtils {

	private RelatorioUtils() {
	}

	public static List<RelatorioVO> converterDadosRelatorio(List<LeituraDTO> leituras, Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (LeituraDTO leitura : leituras) {
			RelatorioVO relatorioVO = new RelatorioVO();

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(leitura.getData());
			
			ConsumoDTO consumo = leitura.getConsumo();

			relatorioVO.setAgua(consumo.getAgua());
			relatorioVO.setEnergia(consumo.getEnergia() / 24000);
			relatorioVO.setTempoUso(consumo.getTempoUso());
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
