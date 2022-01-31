package br.com.vanilla.site.controler.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.vanilla.site.entity.DatasPeriodoComparacao;
import br.com.vanilla.site.entity.DatasPesquisaVO;
import br.com.vanilla.site.entity.IntervaloDatasVO;
import br.com.vanilla.site.entity.RelatorioVO;
import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.model.adapter.DatasAdapter;
import br.com.vanilla.site.service.ConsumoService;
import br.com.vanilla.site.utils.DataUtils;
import br.com.vanilla.site.utils.RelatorioUtils;
import br.com.vanilla.site.utils.UsuarioUtils;

@Controller
public class CompararController {

	private static final String DATAS = "datas";
	private static final String DADOS_RELATORIO = "dadosRelatorio";
	private static final String DADOS_GRAFICO_USO = "dadosGraficoUso";
	private static final String DADOS_GRAFICO_CONSUMO = "dadosGraficoConsumo";

	private ConsumoService consumo;
	private DatasAdapter datasAdapter = new DatasAdapter();
	private List<Object> dadosGraficoConsumo1;
	private List<Object> dadosGraficoUso1;
	private List<RelatorioVO> dadosRelatorio1;
	private List<Object> dadosGraficoConsumo2;
	private List<Object> dadosGraficoUso2;
	private List<RelatorioVO> dadosRelatorio2;
	private DatasPeriodoComparacao datas;
	private Usuario usuarioVO;

	@GetMapping("/comparar")
	public String carregarPagina(Model model, HttpSession session) {
		usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);
		IntervaloDatasVO intervalo = new IntervaloDatasVO();
		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);
		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		consumo = new ConsumoService(intervalo);

		DatasPesquisaVO datasConvertidas = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		DatasPeriodoComparacao datasPeriodos = new DatasPeriodoComparacao();
		datasPeriodos.setDataInicial1(datasConvertidas.getDataInicial());
		datasPeriodos.setDataInicial2(datasConvertidas.getDataInicial());
		datasPeriodos.setDataFinal1(datasConvertidas.getDataFinal());
		datasPeriodos.setDataFinal2(datasConvertidas.getDataFinal());

		dadosGraficoConsumo1 = consumo.obterDadosConsumo();
		dadosGraficoUso1 = consumo.obterDadosUso(usuarioVO.getMeta());
		dadosRelatorio1 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		model.addAttribute(DADOS_GRAFICO_CONSUMO + 1, dadosGraficoConsumo1);
		model.addAttribute(DADOS_GRAFICO_USO + 1, dadosGraficoUso1);
		model.addAttribute(DADOS_RELATORIO + 1, dadosRelatorio1);

		dadosGraficoConsumo2 = consumo.obterDadosConsumo();
		dadosGraficoUso2 = consumo.obterDadosUso(usuarioVO.getMeta());
		dadosRelatorio2 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		model.addAttribute(DADOS_GRAFICO_CONSUMO + 2, dadosGraficoConsumo2);
		model.addAttribute(DADOS_GRAFICO_USO + 2, dadosGraficoUso2);
		model.addAttribute(DADOS_RELATORIO + 2, dadosRelatorio2);

		model.addAttribute(DATAS, datasPeriodos);

		return "comparar";
	}

	@PostMapping("/comparar")
	public String buscar(Model model, HttpSession session, DatasPeriodoComparacao datas) {
		this.datas = datas;
		usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);
		popularDadosPeriodo1(model);
		popularDadosPeriodo2(model);
		return "comparar";
	}

	private void popularDadosPeriodo1(Model model) {
		DatasPesquisaVO datasPesquisa1 = new DatasPesquisaVO();
		datasPesquisa1.setDataInicial(datas.getDataInicial1());
		datasPesquisa1.setDataFinal(datas.getDataFinal1());
		IntervaloDatasVO intervaloDatas1 = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datasPesquisa1);

		consumo = new ConsumoService(intervaloDatas1);

		dadosGraficoConsumo1 = consumo.obterDadosConsumo();
		dadosGraficoUso1 = consumo.obterDadosUso(usuarioVO.getMeta());
		dadosRelatorio1 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());

		model.addAttribute(DADOS_GRAFICO_CONSUMO + 1, dadosGraficoConsumo1);
		model.addAttribute(DADOS_GRAFICO_USO + 1, dadosGraficoUso1);
		model.addAttribute(DADOS_RELATORIO + 1, dadosRelatorio1);
	}

	private void popularDadosPeriodo2(Model model) {
		DatasPesquisaVO datasPesquisa2 = new DatasPesquisaVO();
		datasPesquisa2.setDataInicial(datas.getDataInicial2());
		datasPesquisa2.setDataFinal(datas.getDataFinal2());
		IntervaloDatasVO intervaloDatas2 = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datasPesquisa2);

		consumo = new ConsumoService(intervaloDatas2);
		dadosGraficoConsumo2 = consumo.obterDadosConsumo();
		dadosGraficoUso2 = consumo.obterDadosUso(usuarioVO.getMeta());
		dadosRelatorio2 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());

		model.addAttribute("dadosGraficoConsumo2", dadosGraficoConsumo2);
		model.addAttribute("dadosGraficoUso2", dadosGraficoUso2);
		model.addAttribute("dadosRelatorio2", dadosRelatorio2);
		model.addAttribute(DATAS, datas);
	}

}
