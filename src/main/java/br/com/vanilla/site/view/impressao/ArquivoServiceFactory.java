package br.com.vanilla.site.view.impressao;

import java.util.List;

import br.com.vanilla.site.domain.entity.LeituraDTO;
import br.com.vanilla.site.domain.service.ImpressaoService;

public class ArquivoServiceFactory {

	private ArquivoServiceFactory() {
	}

	public static ImpressaoService createPdf(String dataInicial, String dataFinal, List<LeituraDTO> consumo, int meta) {
		return new ArquivoPDF(dataInicial, dataFinal, consumo, meta);
	}

}
