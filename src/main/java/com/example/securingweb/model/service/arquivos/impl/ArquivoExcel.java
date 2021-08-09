package com.example.securingweb.model.service.arquivos.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.utils.DataUtils;

public class ArquivoExcel extends AbstractArquivoService {

	protected ArquivoExcel(String dataInicial, String dataFinal) {
		super(dataInicial, dataFinal, "xlxs");
	}

	XSSFWorkbook workBook = new XSSFWorkbook();

	@Override
	public void gerar(List<ConsumoVO> lista, int metaUso) {

		byte[] saida = null;

		XSSFSheet sheet = workBook.createSheet("Planilha");

		int celula = 0;

		Row row = sheet.createRow(0);

		Cell colunaTituloData = row.createCell(celula++);
		colunaTituloData.setCellValue("Data");

		Cell colunaTituloAgua = row.createCell(celula++);
		colunaTituloAgua.setCellValue("Água (L)");

		Cell colunaTituloEnergia = row.createCell(celula++);
		colunaTituloEnergia.setCellValue("Energia (W)");

		Cell colunaTituloTempoUso = row.createCell(celula++);
		colunaTituloTempoUso.setCellValue("Tempo de Uso Real (Minutos)");

		Cell colunaTituloMetaUso = row.createCell(celula++);
		colunaTituloMetaUso.setCellValue("Meta de tempo de uso (minutos)");

		int linha = 1;
		for (ConsumoVO item : lista) {
			celula = 0;

			row = sheet.createRow(linha++);

			Cell colunaData = row.createCell(celula++);

			colunaData.setCellValue(DataUtils.formatarData(item.getDia(), item.getMes(), item.getAno()));

			Cell colunaAgua = row.createCell(celula++);
			colunaAgua.setCellValue(item.getAgua());

			Cell colunaEnergia = row.createCell(celula++);
			colunaEnergia.setCellValue(item.getEnergia());

			Cell colunaTempoUso = row.createCell(celula++);
			colunaTempoUso.setCellValue(item.getTempoUso());

			Cell colunaMetaUso = row.createCell(celula++);
			colunaMetaUso.setCellValue(metaUso);
		}

		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			workBook.write(outputStream);

			saida = outputStream.toByteArray();
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.bytes = saida;
	}

}
