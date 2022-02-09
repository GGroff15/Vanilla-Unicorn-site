package br.com.vanilla.site.controler.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vanilla.site.entity.DatasPeriodoComparacao;
import br.com.vanilla.site.entity.DatasPesquisaVO;
import br.com.vanilla.site.entity.IntervaloDTO;
import br.com.vanilla.site.entity.RelatorioVO;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.model.adapter.DatasAdapter;
import br.com.vanilla.site.service.ConsumoService;
import br.com.vanilla.site.utils.DataUtils;
import br.com.vanilla.site.utils.RelatorioUtils;
import br.com.vanilla.site.utils.UsuarioUtils;

@Controller
public class CompararController {

	private static final String VIEW_COMPARAR = "comparar";
	private static final String DATAS = "datas";
	private static final String DADOS_RELATORIO = "dadosRelatorio";
	private static final String DADOS_GRAFICO_USO = "dadosGraficoUso";
	private static final String DADOS_GRAFICO_CONSUMO = "dadosGraficoConsumo";

	private List<Object> dadosGraficoConsumo1;
	private List<Object> dadosGraficoUso1;
	private List<RelatorioVO> dadosRelatorio1;
	private List<Object> dadosGraficoConsumo2;
	private List<Object> dadosGraficoUso2;
	private List<RelatorioVO> dadosRelatorio2;

	private ConsumoService consumo;
	private DatasAdapter datasAdapter = new DatasAdapter();
	private UsuarioDTO usuario;

	@GetMapping("/comparar")
	public ModelAndView carregarPagina(HttpSession session) {
		usuario = UsuarioUtils.recuperarDetalhesUsuario(session);
		IntervaloDTO intervalo = DataUtils.obterIntervaloUltimosTrintaDias();
		consumo = new ConsumoService(intervalo);

		DatasPesquisaVO dataPesquisa = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		DatasPeriodoComparacao datasPeriodos = new DatasPeriodoComparacao();
		datasPeriodos.setDataInicial1(dataPesquisa.getDataInicial());
		datasPeriodos.setDataInicial2(dataPesquisa.getDataInicial());
		datasPeriodos.setDataFinal1(dataPesquisa.getDataFinal());
		datasPeriodos.setDataFinal2(dataPesquisa.getDataFinal());

		obterDadosPeriodo1();
		obterDadosPeriodo2();

		Map<String, Object> modelMap = popularAtributosView(datasPeriodos);

		return new ModelAndView(VIEW_COMPARAR, modelMap);
	}

	@PostMapping("/comparar")
	public ModelAndView buscar(Model model, HttpSession session, DatasPeriodoComparacao datas) throws ParseException {
		usuario = UsuarioUtils.recuperarDetalhesUsuario(session);

		processarPeriodo1(datas);
		processarPeriodo2(datas);

		Map<String, Object> modelMap = popularAtributosView(datas);
		return new ModelAndView(VIEW_COMPARAR, modelMap);
	}

	private void processarPeriodo1(DatasPeriodoComparacao datas) throws ParseException {
		Date inicial1 = DataUtils.converter(datas.getDataInicial1());
		Date final1 = DataUtils.converter(datas.getDataFinal1());

		IntervaloDTO intervalo1 = new IntervaloDTO(inicial1, final1);
		consumo = new ConsumoService(intervalo1);
		obterDadosPeriodo1();
	}

	private void processarPeriodo2(DatasPeriodoComparacao datas) throws ParseException {
		Date inicial2 = DataUtils.converter(datas.getDataInicial2());
		Date final2 = DataUtils.converter(datas.getDataFinal2());

		IntervaloDTO intervalo2 = new IntervaloDTO(inicial2, final2);
		consumo = new ConsumoService(intervalo2);
		obterDadosPeriodo2();
	}

	private void obterDadosPeriodo1() {
		dadosGraficoConsumo1 = consumo.obterDadosConsumo();
		dadosGraficoUso1 = consumo.obterDadosUso(usuario.getMeta());
		dadosRelatorio1 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuario.getMeta());
	}

	private void obterDadosPeriodo2() {
		dadosGraficoConsumo2 = consumo.obterDadosConsumo();
		dadosGraficoUso2 = consumo.obterDadosUso(usuario.getMeta());
		dadosRelatorio2 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuario.getMeta());
	}

	private Map<String, Object> popularAtributosView(DatasPeriodoComparacao datasPeriodos) {
		Map<String, Object> modelMap = new HashMap<>();

		modelMap.put(DADOS_GRAFICO_CONSUMO + 1, dadosGraficoConsumo1);
		modelMap.put(DADOS_GRAFICO_USO + 1, dadosGraficoUso1);
		modelMap.put(DADOS_RELATORIO + 1, dadosRelatorio1);
		modelMap.put(DADOS_GRAFICO_CONSUMO + 2, dadosGraficoConsumo2);
		modelMap.put(DADOS_GRAFICO_USO + 2, dadosGraficoUso2);
		modelMap.put(DADOS_RELATORIO + 2, dadosRelatorio2);
		modelMap.put(DATAS, datasPeriodos);
		return modelMap;
	}

}
