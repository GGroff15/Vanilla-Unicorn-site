package com.example.securingweb.controler.impl;

import static com.example.securingweb.model.constants.Constants.ARQUIVO_TIPO_PDF;

import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.controler.IController;
import com.example.securingweb.model.adapter.DatasAdapter;
import com.example.securingweb.model.entity.ConsumoVO;
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

	@Autowired
	Consumo consumo;

	@Autowired
	DatasAdapter datasAdapter;

	List<ConsumoVO> consumoPeriodo;
	List<Object> dadosGraficoConsumo;
	List<Object> dadosGraficoUso;
	List<RelatorioVO> dadosRelatorio;
	private DatasPesquisaVO datas;

	@GetMapping("/home")
	public String carregarPagina(Model model, HttpSession session) {
		IntervaloDatasVO intervalo = new IntervaloDatasVO();

		Long dataAtual = DataUtils.dataAtual();
		Long trintaDiasAntes = DataUtils.trintaDiasAntes(dataAtual);

		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);

		intervalo.setDataInicial(trintaDiasAntes);
		intervalo.setDataFinal(dataAtual);

		DatasPesquisaVO datasConvertidas = datasAdapter.converterIntervaloDatasVoParaDatasPesquisaVo(intervalo);

		this.datas = datasConvertidas;

		consumoPeriodo = consumo.consultar(intervalo);

		consumoPeriodo.sort(Comparator.comparing(ConsumoVO::getData));

		dadosGraficoConsumo = consumo.converterDadosGraficoConsumo(consumoPeriodo);
		model.addAttribute("dadosGraficoConsumo", dadosGraficoConsumo);

		dadosGraficoUso = consumo.converterDadosGraficoUso(consumoPeriodo, usuarioVO.getMeta());
		model.addAttribute("dadosGraficoUso", dadosGraficoUso);

		dadosRelatorio = RelatorioUtils.converterDadosRelatorio(consumoPeriodo, usuarioVO.getMeta());
		model.addAttribute("dadosRelatorio", dadosRelatorio);

		model.addAttribute("datas", datas);

		return "consulta";

	}

	@PostMapping("/home")
	public String buscar(Model model, HttpSession session, DatasPesquisaVO datas) {

		this.datas = datas;

		IntervaloDatasVO intervaloDatasVO = datasAdapter.converterDatasPesquisaVoParaIntervaloDatasVo(datas);

		UsuarioVO usuarioVO = UsuarioUtils.recuperarDetalhesUsuario(session);

		consumoPeriodo = consumo.consultar(intervaloDatasVO);

		dadosGraficoConsumo = consumo.converterDadosGraficoConsumo(consumoPeriodo);
		model.addAttribute("dadosGraficoConsumo", dadosGraficoConsumo);

		dadosGraficoUso = consumo.converterDadosGraficoUso(consumoPeriodo, usuarioVO.getMeta());
		model.addAttribute("dadosGraficoUso", dadosGraficoUso);

		dadosRelatorio = consumo.converterDadosRelatorio(consumoPeriodo, usuarioVO.getMeta());
		model.addAttribute("dadosRelatorio", dadosRelatorio);

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
		arquivo.gerar(consumoPeriodo, usuarioVO.getMeta());
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
