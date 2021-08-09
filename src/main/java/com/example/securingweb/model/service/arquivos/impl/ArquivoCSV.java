package com.example.securingweb.model.service.arquivos.impl;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.utils.DataUtils;
import com.opencsv.CSVWriter;

public class ArquivoCSV extends AbstractArquivoService {

	protected ArquivoCSV(String dataInicio, String dataFim) {
		super(dataInicio, dataFim, "csv");
	}

	@Override
	public void gerar(List<ConsumoVO> consumoVOs, int meta) {

		byte[] saida = null;

		String[] cabecalho = { "Data", "Água(L)", "Energia (kW)", "Tempo Real de Uso (minutos)",
				"Meta tempo de uso (minutos)" };

		List<String[]> linhas = new ArrayList<>();
		for (ConsumoVO item : consumoVOs) {
			linhas.add(new String[] { DataUtils.formatarData(item.getDia(), item.getMes(), item.getAno()),
					String.valueOf(item.getAgua()), String.valueOf(item.getEnergia()),
					String.valueOf(item.getTempoUso()), String.valueOf(meta) });
		}

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			Writer writer = new BufferedWriter(new OutputStreamWriter(baos));
			CSVWriter csvWriter = new CSVWriter(writer);

			csvWriter.writeNext(cabecalho);
			csvWriter.writeAll(linhas);

			csvWriter.flush();
			csvWriter.close();

			writer.close();

			saida = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.bytes = saida;
	}

}
