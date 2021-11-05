package com.example.securingweb.controler.impl;

import static com.example.securingweb.model.constants.Constants.ARQUIVO_TIPO_PDF;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.controler.IController;
import com.example.securingweb.model.adapter.DatasAdapter;
import com.example.securingweb.model.entity.DatasPesquisaVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.exception.ExtensaoInvalidaException;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.service.arquivos.impl.ArquivoServiceFactory;
import com.example.securingweb.model.service.consumo.Consumo;
import com.example.securingweb.utils.DataUtils;
import com.example.securingweb.utils.RelatorioUtils;
import com.example.securingweb.utils.UsuarioUtils;

@Controller
@Scope("session")
public class ConsultaController implements IController {

	Consumo consumo;

	DatasAdapter datasAdapter = new DatasAdapter();

	private List<Object> dadosGraficoConsumo;
	private List<Object> dadosGraficoUso;
	private List<RelatorioVO> dadosRelatorio;
	private DatasPesquisaVO datas;
	private double agua;
	private double energia;
	private int totalMinutos;
	private int totalDias;

	@GetMapping("/home")
	public String carregarPagina(Model model, HttpSession session) {
		IntervaloDatasVO intervalo = new IntervaloDatasVO();

		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);
		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);

		DatasPesquisaVO datasConvertidas = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		this.datas = datasConvertidas;

		consumo = new Consumo(intervalo);

		dadosGraficoConsumo = consumo.obterDadosGraficoConsumo();
		model.addAttribute("dadosGraficoConsumo", dadosGraficoConsumo);

		dadosGraficoUso = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		model.addAttribute("dadosGraficoUso", dadosGraficoUso);

		dadosRelatorio = RelatorioUtils.converterDadosRelatorio(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		model.addAttribute("dadosRelatorio", dadosRelatorio);

		agua = consumo.obterTotalConsumoAgua();
		model.addAttribute("consumoAguaPeriodo", agua);

		energia = consumo.calcularEnergiaPeriodo();
		model.addAttribute("consumoEnergiaPeriodo", energia);

		totalMinutos = consumo.calcularTotalMinutos();
		model.addAttribute("totalMinutosPeriodo", totalMinutos);

		totalDias = consumo.calcularQuantidadeDias();
		model.addAttribute("diasPeriodo", totalDias);

		model.addAttribute("datas", datas);

		return "consulta";

	}

	@PostMapping("/home")
	public String buscar(Model model, HttpSession session, DatasPesquisaVO datas) {
		this.datas = datas;

		IntervaloDatasVO intervaloDatasVO = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datas);

		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);

		consumo = new Consumo(intervaloDatasVO);

		dadosGraficoConsumo = consumo.obterDadosGraficoConsumo();
		model.addAttribute("dadosGraficoConsumo", dadosGraficoConsumo);

		dadosGraficoUso = consumo.obterDadosGraficoUso(usuarioVO.getMeta());
		model.addAttribute("dadosGraficoUso", dadosGraficoUso);

		dadosRelatorio = consumo.converterDadosRelatorio(usuarioVO.getMeta());
		model.addAttribute("dadosRelatorio", dadosRelatorio);

		agua = consumo.obterTotalConsumoAgua();
		model.addAttribute("consumoAguaPeriodo", agua);

		energia = consumo.calcularEnergiaPeriodo();
		model.addAttribute("consumoEnergiaPeriodo", energia);

		totalMinutos = consumo.calcularTotalMinutos();
		model.addAttribute("totalMinutosPeriodo", totalMinutos);

		totalDias = consumo.calcularQuantidadeDias();
		model.addAttribute("diasPeriodo", totalDias);

		model.addAttribute("datas", datas);

		return "consulta";
	}

	@GetMapping("/download")
	public HttpEntity<byte[]> downloadRelatorio(HttpSession session) {
		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);
		AbstractArquivoService arquivo = criarArquivoFactory();
		byte[] arquivoBytes = criarRelatorioConsumo(usuarioVO, arquivo);
		return criarResponse(arquivo, arquivoBytes);
	}

	private HttpEntity<byte[]> criarResponse(AbstractArquivoService arquivo, byte[] arquivoBytes) {
		HttpHeaders httpHeaders = criarHeadersResponse(arquivo);
		return criarEntidadeResponse(arquivoBytes, httpHeaders);
	}

	private HttpEntity<byte[]> criarEntidadeResponse(byte[] arquivoBytes, HttpHeaders httpHeaders) {
		return new HttpEntity<>(arquivoBytes, httpHeaders);
	}

	private HttpHeaders criarHeadersResponse(AbstractArquivoService arquivo) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Disposition", "attachment;filename=\"" + arquivo.getNomeArquivo() + "\"");
		return httpHeaders;
	}

	private byte[] criarRelatorioConsumo(UsuarioVO usuarioVO, AbstractArquivoService arquivo) {
		arquivo.gerar(consumo.getConsumoPeriodo(), usuarioVO.getMeta());
		return arquivo.getBytes();
	}

	private AbstractArquivoService criarArquivoFactory() {
		AbstractArquivoService arquivo = null;
		try {
			arquivo = ArquivoServiceFactory.create(datas.getDataInicial(), datas.getDataFinal(), ARQUIVO_TIPO_PDF);
		} catch (ExtensaoInvalidaException e) {
			e.printStackTrace();
		}
		return arquivo;
	}

	@Override
	public String carregarPagina(Model model) {
		return null;
	}

	public DatasPesquisaVO getDatas() {
		return datas;
	}

	public void setDatas(DatasPesquisaVO datas) {
		this.datas = datas;
	}
}
