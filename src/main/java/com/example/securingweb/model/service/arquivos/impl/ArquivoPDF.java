package com.example.securingweb.model.service.arquivos.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.utils.RelatorioUtils;
import com.lowagie.text.DocumentException;

public class ArquivoPDF extends AbstractArquivoService {

	protected ArquivoPDF(String dataInicial, String dataFinal) {
		super(dataInicial, dataFinal, "pdf");
	}

	@Override
	public void gerar(List<ConsumoVO> consumo, int meta) {

		List<RelatorioVO> dadosRelatorio = RelatorioUtils.converterDadosRelatorio(consumo, meta);

		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable("dadosRelatorio", dadosRelatorio);

		String html = templateEngine.process("Relatorio_Template", context);

		// --------------------------------------------------------------------------------------------

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		ITextRenderer renderer = new ITextRenderer();
		renderer.setDocumentFromString(html);
		renderer.layout();
		try {
			renderer.createPDF(outputStream);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}

		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		super.bytes = outputStream.toByteArray();
	}
}
