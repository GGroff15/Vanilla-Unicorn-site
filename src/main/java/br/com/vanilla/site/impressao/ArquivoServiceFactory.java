package br.com.vanilla.site.impressao;

import java.util.List;

import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.service.ImpressaoService;

public class ArquivoServiceFactory {

	private ArquivoServiceFactory() {
	}

	public static ImpressaoService createPdf(String dataInicial, String dataFinal, List<ConsumoVO> consumo, int meta) {
		return new ArquivoPDF(dataInicial, dataFinal, consumo, meta);
	}

}
