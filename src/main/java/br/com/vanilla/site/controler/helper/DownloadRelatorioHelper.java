package br.com.vanilla.site.controler.helper;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.DatasPesquisaVO;
import br.com.vanilla.site.entity.Relatorio;
import br.com.vanilla.site.impressao.ArquivoServiceFactory;
import br.com.vanilla.site.service.ImpressaoService;

public class DownloadRelatorioHelper {

	private Relatorio relatorio;

	public DownloadRelatorioHelper(DatasPesquisaVO datas, int meta, List<ConsumoVO> consumo) {
		ImpressaoService arquivo = ArquivoServiceFactory.createPdf(datas.getDataInicial(), datas.getDataFinal(), consumo,
				meta);
		relatorio = arquivo.gerar();
	}

	public HttpEntity<byte[]> obterRelatorio() {
		HttpHeaders httpHeaders = criarHeadersResponse();
		return criarEntidadeResponse(relatorio.getBytes(), httpHeaders);
	}

	private HttpHeaders criarHeadersResponse() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=\"" + relatorio.getNome() + "\"");
		return httpHeaders;
	}

	private HttpEntity<byte[]> criarEntidadeResponse(byte[] arquivoBytes, HttpHeaders httpHeaders) {
		return new HttpEntity<>(arquivoBytes, httpHeaders);
	}

}
