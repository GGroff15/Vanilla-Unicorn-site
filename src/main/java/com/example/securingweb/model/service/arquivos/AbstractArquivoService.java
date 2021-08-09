package com.example.securingweb.model.service.arquivos;

import java.util.List;

import com.example.securingweb.model.entity.ConsumoVO;

public abstract class AbstractArquivoService {

	private String nomeArquivo;
	protected byte[] bytes = null;

	/**
	 * Construtor
	 * 
	 * @param dataInicial
	 * @param dataFinal
	 * @param extensao    do arquivo que será criado (ex: csv)
	 *                    </p>
	 */
	public AbstractArquivoService(String dataInicial, String dataFinal, String extensao) {
		StringBuilder builder = new StringBuilder();
		builder.append("relatorio_consumo_perido_");
		builder.append(dataInicial);
		builder.append("_");
		builder.append(dataFinal);
		builder.append(".");
		builder.append(extensao);
		nomeArquivo = builder.toString();
	}

	public abstract void gerar(List<ConsumoVO> consumo, int meta);

	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public byte[] getBytes() {
		return bytes;
	}

}
