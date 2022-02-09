package br.com.vanilla.site.service;

import java.util.List;

import br.com.vanilla.site.entity.LeituraDTO;
import br.com.vanilla.site.entity.Relatorio;

public abstract class ImpressaoService {

	protected Relatorio relatorio;
	protected List<LeituraDTO> leituras;
	protected int meta;

	/**
	 * Construtor
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @param extensao    do arquivo que será criado (ex: csv)
	 *                    </p>
	 */
	protected ImpressaoService(String dataInicial, String dataFinal, String extensao) {
		relatorio = new Relatorio();
		StringBuilder builder = new StringBuilder();
		builder.append("relatorio_consumo_perido_");
		builder.append(dataInicial);
		builder.append("_");
		builder.append(dataFinal);
		builder.append(".");
		builder.append(extensao);
		relatorio.setNome(builder.toString());
	}

	public Relatorio gerar() {
		gerarArquivo(leituras, meta);
		return relatorio;
	}

	protected abstract void gerarArquivo(List<LeituraDTO> leituras, int meta);
}
