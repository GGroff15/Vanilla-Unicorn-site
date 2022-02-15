package br.com.vanilla.site.domain.service.facade;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.vanilla.site.domain.entity.ConsumoViewData;
import br.com.vanilla.site.domain.entity.IntervaloDTO;
import br.com.vanilla.site.domain.entity.LeituraDTO;
import br.com.vanilla.site.domain.entity.RelatorioVO;
import br.com.vanilla.site.domain.service.ConsumoService;

@Component
public class ConsumoFacade {

	private ConsumoService consumoService;
	private ConsumoViewData consumo;

	public ConsumoFacade(ConsumoService consumoService, ConsumoViewData consumo) {
		this.consumoService = consumoService;
		this.consumo = consumo;
	}

	public ConsumoViewData obterDados(IntervaloDTO intervalo, int meta) {

		consumoService.setIntervalo(intervalo);

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
