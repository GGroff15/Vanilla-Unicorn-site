package br.com.vanilla.site.service.facade;

import java.util.List;

import br.com.vanilla.site.entity.Consumo;
import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.IntervaloDatasVO;
import br.com.vanilla.site.entity.RelatorioVO;
import br.com.vanilla.site.service.ConsumoService;

public class ConsumoFacade {

	private ConsumoService consumoService;
	private int meta;

	public ConsumoFacade(IntervaloDatasVO intervaloDatasVO, int meta) {
		this.meta = meta;
		consumoService = new ConsumoService(intervaloDatasVO);
	}

	public Consumo obterDados() {
		Consumo consumo = new Consumo();

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

		List<ConsumoVO> consumoPeriodo = consumoService.getConsumoPeriodo();
		consumo.setConsumoPeriodo(consumoPeriodo);

		return consumo;
	}

}
