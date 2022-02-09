package br.com.vanilla.site.impressao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import br.com.vanilla.site.entity.ConsumoVO;
import br.com.vanilla.site.entity.RelatorioVO;
import br.com.vanilla.site.service.ImpressaoService;
import br.com.vanilla.site.utils.RelatorioUtils;

public class ArquivoPDF extends ImpressaoService {

	protected ArquivoPDF(String dataInicial, String dataFinal, List<ConsumoVO> consumo, int meta) {
		super(dataInicial, dataFinal, "pdf");
		super.leituras = consumo;
		super.meta = meta;
	}

	@Override
	public void gerarArquivo(List<ConsumoVO> consumo, int meta) {

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

		super.relatorio.setBytes(outputStream.toByteArray());
	}

}
