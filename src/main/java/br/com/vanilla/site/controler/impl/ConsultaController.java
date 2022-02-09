package br.com.vanilla.site.controler.impl;

import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.vanilla.site.controler.helper.DownloadRelatorioHelper;
import br.com.vanilla.site.entity.ConsumoViewData;
import br.com.vanilla.site.entity.DatasPesquisaVO;
import br.com.vanilla.site.entity.IntervaloDTO;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.model.adapter.DatasAdapter;
import br.com.vanilla.site.service.facade.ConsumoFacade;
import br.com.vanilla.site.utils.DataUtils;
import br.com.vanilla.site.utils.UsuarioUtils;

@Controller
@Scope("session")
public class ConsultaController {

	private static final String ATRIBUTO_DATAS = "datas";
	private static final String ATRIBUTO_DIAS_PERIODO = "diasPeriodo";
	private static final String ATRIBUTO_TOTAL_MINUTOS_PERIODO = "totalMinutosPeriodo";
	private static final String ATRIBUTO_CONSUMO_ENERGIA_PERIODO = "consumoEnergiaPeriodo";
	private static final String ATRIBUTO_CONSUMO_AGUA_PERIODO = "consumoAguaPeriodo";
	private static final String ATRIBUTO_DADOS_RELATORIO = "dadosRelatorio";
	private static final String ATRIBUTO_DADOS_GRAFICO_USO = "dadosGraficoUso";
	private static final String ATRIBUTO_DADOS_GRAFICO_CONSUMO = "dadosGraficoConsumo";

	private DatasAdapter datasAdapter = new DatasAdapter();
	private DatasPesquisaVO datas;
	private ConsumoViewData consumo;

	@GetMapping("/home")
	public String carregarPagina(Model model, HttpSession session) {
		UsuarioDTO usuario = UsuarioUtils.recuperarDetalhesUsuario(session);
		IntervaloDTO intervalo = DataUtils.obterIntervaloUltimosTrintaDias();
		this.datas = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		ConsumoFacade consumoFacade = new ConsumoFacade(intervalo, usuario.getMeta());
		consumo = consumoFacade.obterDados();
		popularAtributos(model);

		return "consulta";
	}

	@PostMapping("/home")
	public String buscar(Model model, HttpSession session, DatasPesquisaVO datas) throws ParseException {
		this.datas = datas;
		UsuarioDTO usuario = UsuarioUtils.recuperarDetalhesUsuario(session);
		IntervaloDTO intervalo = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datas);

		ConsumoFacade consumoFacade = new ConsumoFacade(intervalo, usuario.getMeta());
		consumo = consumoFacade.obterDados();
		popularAtributos(model);

		return "consulta";
	}

	private void popularAtributos(Model model) {
		model.addAttribute(ATRIBUTO_DADOS_GRAFICO_CONSUMO, consumo.getDadosConsumo());
		model.addAttribute(ATRIBUTO_DADOS_GRAFICO_USO, consumo.getDadosUso());
		model.addAttribute(ATRIBUTO_DADOS_RELATORIO, consumo.getRelatorio());
		model.addAttribute(ATRIBUTO_CONSUMO_AGUA_PERIODO, consumo.getAgua());
		model.addAttribute(ATRIBUTO_CONSUMO_ENERGIA_PERIODO, consumo.getEnergia());
		model.addAttribute(ATRIBUTO_TOTAL_MINUTOS_PERIODO, consumo.getTempoUsoTotal());
		model.addAttribute(ATRIBUTO_DIAS_PERIODO, consumo.getTotalDias());
		model.addAttribute(ATRIBUTO_DATAS, datas);
	}

	@GetMapping("/download")
	public HttpEntity<byte[]> downloadRelatorio(HttpSession session) {
		UsuarioDTO usuario = UsuarioUtils.recuperarDetalhesUsuario(session);
		DownloadRelatorioHelper relatorioHelper = new DownloadRelatorioHelper(datas, usuario.getMeta(),
				consumo.getConsumoPeriodo());
		return relatorioHelper.obterRelatorio();
	}

}
