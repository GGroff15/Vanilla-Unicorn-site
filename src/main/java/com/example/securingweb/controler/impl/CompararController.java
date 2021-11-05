package com.example.securingweb.controler.impl;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.controler.IController;
import com.example.securingweb.model.adapter.DatasAdapter;
import com.example.securingweb.model.entity.DatasPeriodoComparacao;
import com.example.securingweb.model.entity.DatasPesquisaVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.consumo.Consumo;
import com.example.securingweb.utils.DataUtils;
import com.example.securingweb.utils.RelatorioUtils;
import com.example.securingweb.utils.UsuarioUtils;

@Controller
public class CompararController implements IController {

	private static final String DATAS = "datas";

	private static final String DADOS_RELATORIO = "dadosRelatorio";

	private static final String DADOS_GRAFICO_USO = "dadosGraficoUso";

	private static final String DADOS_GRAFICO_CONSUMO = "dadosGraficoConsumo";

	Consumo consumo;

	DatasAdapter datasAdapter = new DatasAdapter();

	private List<Object> dadosGraficoConsumo1;
	private List<Object> dadosGraficoUso1;
	private List<RelatorioVO> dadosRelatorio1;
	private List<Object> dadosGraficoConsumo2;
	private List<Object> dadosGraficoUso2;
	private List<RelatorioVO> dadosRelatorio2;

	@GetMapping("/comparar")
	public String carregarPagina(Model model, HttpSession session) {
		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);
		IntervaloDatasVO intervalo = new IntervaloDatasVO();
		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);
		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		consumo = new Consumo(intervalo);

		DatasPesquisaVO datasConvertidas = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		DatasPeriodoComparacao datasPeriodos = new DatasPeriodoComparacao();
		datasPeriodos.setDataInicial1(datasConvertidas.getDataInicial());
		datasPeriodos.setDataInicial2(datasConvertidas.getDataInicial());
		datasPeriodos.setDataFinal1(datasConvertidas.getDataFinal());
		datasPeriodos.setDataFinal2(datasConvertidas.getDataFinal());

		dadosGraficoConsumo1 = consumo.obterDadosGraficoConsumo();
		dadosGraficoUso1 = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		dadosRelatorio1 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		model.addAttribute(DADOS_GRAFICO_CONSUMO + 1, dadosGraficoConsumo1);
		model.addAttribute(DADOS_GRAFICO_USO + 1, dadosGraficoUso1);
		model.addAttribute(DADOS_RELATORIO + 1, dadosRelatorio1);

		dadosGraficoConsumo2 = consumo.obterDadosGraficoConsumo();
		dadosGraficoUso2 = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		dadosRelatorio2 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		model.addAttribute(DADOS_GRAFICO_CONSUMO + 2, dadosGraficoConsumo2);
		model.addAttribute(DADOS_GRAFICO_USO + 2, dadosGraficoUso2);
		model.addAttribute(DADOS_RELATORIO + 2, dadosRelatorio2);

		model.addAttribute(DATAS, datasPeriodos);

		return "comparar";
	}

	@PostMapping("/comparar")
	public String buscar(Model model, HttpSession session, DatasPeriodoComparacao datas) {
		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);
		DatasPesquisaVO datasPesquisa1 = new DatasPesquisaVO();
		datasPesquisa1.setDataInicial(datas.getDataInicial1());
		datasPesquisa1.setDataFinal(datas.getDataFinal1());
		IntervaloDatasVO intervaloDatas1 = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datasPesquisa1);

		consumo = new Consumo(intervaloDatas1);

		dadosGraficoConsumo1 = consumo.obterDadosGraficoConsumo();
		dadosGraficoUso1 = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		dadosRelatorio1 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());

		model.addAttribute(DADOS_GRAFICO_CONSUMO + 1, dadosGraficoConsumo1);
		model.addAttribute(DADOS_GRAFICO_USO + 1, dadosGraficoUso1);
		model.addAttribute(DADOS_RELATORIO + 1, dadosRelatorio1);

		DatasPesquisaVO datasPesquisa2 = new DatasPesquisaVO();
		datasPesquisa2.setDataInicial(datas.getDataInicial2());
		datasPesquisa2.setDataFinal(datas.getDataFinal2());
		IntervaloDatasVO intervaloDatas2 = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datasPesquisa2);

		consumo = new Consumo(intervaloDatas2);
		dadosGraficoConsumo2 = consumo.obterDadosGraficoConsumo();
		dadosGraficoUso2 = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		dadosRelatorio2 = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());

		model.addAttribute("dadosGraficoConsumo2", dadosGraficoConsumo2);
		model.addAttribute("dadosGraficoUso2", dadosGraficoUso2);
		model.addAttribute("dadosRelatorio2", dadosRelatorio2);
		model.addAttribute(DATAS, datas);

		return "comparar";
	}

	@Override
	public String carregarPagina(Model model) {
		return null;
	}

}
