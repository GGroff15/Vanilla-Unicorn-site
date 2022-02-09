package br.com.vanilla.site.service.facade;

import java.util.List;

import br.com.vanilla.site.entity.ConsumoViewData;
import br.com.vanilla.site.entity.IntervaloDTO;
import br.com.vanilla.site.entity.LeituraDTO;
import br.com.vanilla.site.entity.RelatorioVO;
import br.com.vanilla.site.service.ConsumoService;

public class ConsumoFacade {

	private ConsumoService consumoService;
	private int meta;

	public ConsumoFacade(IntervaloDTO intervalo, int meta) {
		this.meta = meta;
		consumoService = new ConsumoService(intervalo);
	}

	public ConsumoViewData obterDados() {

		ConsumoViewData consumo = new ConsumoViewData();

		double consumoEnergiaPeriodo = consumoService.consumoEnergiaPeriodo();
		consumo.setEnergia(consumoEnergiaPeriodo);

		int totalDias = consumoService.calcularQuantidadeDias();
		consumo.setTotalDias(totalDias);

		int tempoUsoTotal = consumoService.calcularTempoUsoTotal();
		consumo.setTempoUsoTotal(tempoUsoTotal);

		List<RelatorioVO> dadosRelatorio = consumoService.obterDadosRelatorio(meta);
		consumo.setRelatorio(dadosRelatorio);

		List<Object> dadosConsumo = consumoService.obterDadosConsumo();
		consumo.setDadosConsumo(dadosConsumo);

		List<Object> dadosUso = consumoService.obterDadosUso(meta);
		consumo.setDadosUso(dadosUso);

		double consumoAgua = consumoService.obterTotalConsumoAgua();
		consumo.setAgua(consumoAgua);

		List<LeituraDTO> consumoPeriodo = consumoService.getConsumoPeriodo();
		consumo.setConsumoPeriodo(consumoPeriodo);

		return consumo;
	}

}
