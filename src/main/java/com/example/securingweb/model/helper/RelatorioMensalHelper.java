package com.example.securingweb.model.helper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.example.securingweb.model.constants.Constants;
import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.DicaConsumo;
import com.example.securingweb.model.entity.FiltroRelatorio;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.exception.ExtensaoInvalidaException;
import com.example.securingweb.model.service.arquivos.AbstractArquivoService;
import com.example.securingweb.model.service.arquivos.impl.ArquivoFactory;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;
import com.example.securingweb.model.utils.DataUtils;

public class RelatorioMensalHelper {

	private static final String NO_DIA = "No dia ";
	private static final String LITROS_E = " Litros e ";
	private static final String DD_MM_YYYY = "dd-MM-yyyy";
	private IntervaloDatasVO intervalo;
	private String dataInicial;
	private String dataFinal;
	private FiltroRelatorio filtro;
	private Dao<ConsumoVO, FiltroRelatorio> dao;
	private List<ConsumoVO> consumoIntervalo;
	private AbstractArquivoService arquivo = null;
	private int meta;
	private int mes;
	private String username;

	public RelatorioMensalHelper(int mes, String username, int meta) {
		this.meta = meta;
		this.mes = mes;
		this.username = username;
		intervalo = new IntervaloDatasVO();
		filtro = new FiltroRelatorio();
		dao = FactoryDao.criarRelatorioDao();
	}

	public AbstractArquivoService criarRelatorioMensal() {
		setPrimeiroDiaMes();
		setUltimoDiaMes();
		carregarDadosFiltro();
		obterConsumoIntervalo();
		criarArquivoPdf();
		return arquivo;
	}

	private void setUltimoDiaMes() {
		Date dateFinal = DataUtils.getDataUltimoDiaMes(mes);
		long dataMilisegundos = obterDataEmMilisegundos(dateFinal);
		setDataFinalIntervalo(dataMilisegundos);
		setDataFinal(dataMilisegundos);
	}

	private void setDataFinal(long dataMilisegundos) {
		dataFinal = DataUtils.formatarData(DD_MM_YYYY, dataMilisegundos);
	}

	private void setDataFinalIntervalo(long dataMilisegundos) {
		intervalo.setDataFinal(dataMilisegundos);
	}

	private void setPrimeiroDiaMes() {
		Date dateInicial = DataUtils.getDataPrimeiroDiaMes(mes);
		long dataMilisegundos = obterDataEmMilisegundos(dateInicial);
		setDataInicialIntervalo(dataMilisegundos);
		setDataInicial(dataMilisegundos);
	}

	private void setDataInicial(long dataMilisegundos) {
		dataInicial = DataUtils.formatarData(DD_MM_YYYY, dataMilisegundos);
	}

	private void setDataInicialIntervalo(long dataMilisegundos) {
		intervalo.setDataInicial(dataMilisegundos);
	}

	private long obterDataEmMilisegundos(Date dateInicial) {
		return dateInicial.getTime();
	}

	private void carregarDadosFiltro() {
		filtro.setIntervaloDatasVO(intervalo);
		filtro.setUsername(username);
	}

	private void obterConsumoIntervalo() {
		consumoIntervalo = dao.get(filtro);
	}

	private void criarArquivoPdf() {
		try {
			arquivo = ArquivoFactory.create(dataInicial, dataFinal, Constants.ARQUIVO_TIPO_PDF);
			arquivo.gerar(consumoIntervalo, meta);
		} catch (ExtensaoInvalidaException e) {
			e.printStackTrace();
		}
	}

	private String converterMesNumeroMesNome(int mes) {
		switch (mes) {
		case 0:
			return "Janeiro";
		case 1:
			return "Fevereiro";
		case 2:
			return "Março";
		case 3:
			return "Abril";
		case 4:
			return "Maio";
		case 5:
			return "Junho";
		case 6:
			return "Julho";
		case 7:
			return "Agosto";
		case 8:
			return "Setembro";
		case 9:
			return "Outubro";
		case 10:
			return "Novembro";
		case 11:
			return "Dezembro";

		default:
			return String.valueOf(mes);
		}
	}

	public String criarTextoRelatorio() {
		String titulo = "";
		String primeiraLinha = "";
		String textoUsoAcimaMeta = "";
		String textoDetalheUsoAcimaMeta = "";
		String dicaReducaoConsumo = "";
		String maisDetalhes = "";

		List<ConsumoVO> diasConsumoAcimaMeta = new ArrayList<>();
		double totalAgua = 0.0;
		double totalEnergia = 0.0;

		titulo = "<h1>Relatorio de Consumo do Mês de " + converterMesNumeroMesNome(mes) + " de "
				+ Calendar.getInstance().get(Calendar.YEAR) + "</h1>";

		for (ConsumoVO consumoVO : consumoIntervalo) {
			totalAgua = totalAgua + consumoVO.getAgua();
			totalEnergia = totalEnergia + consumoVO.getEnergia();

			if (consumoVO.getTempoUso() > meta) {
				diasConsumoAcimaMeta.add(consumoVO);
			}
		}

		primeiraLinha = "<p>No periodo de " + dataInicial + " á  " + dataFinal + ", o consumo total foi de: "
				+ totalAgua + LITROS_E + totalEnergia + " Watts</p>";

		if (!diasConsumoAcimaMeta.isEmpty()) {

			if (diasConsumoAcimaMeta.size() > 1) {
				StringBuilder builderDiasUsoAcimaMeta = new StringBuilder();
				StringBuilder builderDetalhediaUsoAcimaMeta = new StringBuilder();

				builderDiasUsoAcimaMeta.append("<p>Nos dias ");

				for (int i = 0; i < diasConsumoAcimaMeta.size(); i++) {
					ConsumoVO consumo = diasConsumoAcimaMeta.get(i);
					builderDiasUsoAcimaMeta.append(String.valueOf(consumo.getDia()));
					if (i < (diasConsumoAcimaMeta.size() - 1)) {
						builderDiasUsoAcimaMeta.append(", ");
					}

					builderDetalhediaUsoAcimaMeta.append("<p>" + NO_DIA + consumo.getDia() + " o uso foi de "
							+ consumo.getTempoUso() + "minutos. Com um consumo de " + consumo.getAgua() + LITROS_E
							+ consumo.getEnergia() + " Watts</p>");
				}

				builderDiasUsoAcimaMeta
						.append(" o tempo de uso do chuveiro foi superior a meta de " + meta + " minutos por dia.</p>");

				textoUsoAcimaMeta = builderDiasUsoAcimaMeta.toString();
				textoDetalheUsoAcimaMeta = builderDetalhediaUsoAcimaMeta.toString();

			} else {
				textoUsoAcimaMeta = "<p>" + NO_DIA + diasConsumoAcimaMeta.get(0)
						+ " o tempo de uso do chuveiro foi superior a meta de " + meta + " minutos por dia";
				textoDetalheUsoAcimaMeta = NO_DIA + diasConsumoAcimaMeta.get(0).getDia() + " o uso foi de "
						+ diasConsumoAcimaMeta.get(0).getTempoUso() + "minutos. Com um consumo de "
						+ diasConsumoAcimaMeta.get(0).getAgua() + LITROS_E + diasConsumoAcimaMeta.get(0).getEnergia()
						+ " Watts</p>";
			}

		}

		Dao<DicaConsumo, Integer> daoDicaConsumo = FactoryDao.criarDicaConsumoDao();
		List<DicaConsumo> dicas = daoDicaConsumo.getAll();

		Random random = new Random();
		int dicaNumero = random.nextInt(dicas.size());

		dicaReducaoConsumo = "<p>" + dicas.get(dicaNumero) + "</p>";

		maisDetalhes = "<p>Para detalhe de consumo diario, por favor verifique o relatorio detalhado em anexo ou acesse o site</p>";

		return titulo + primeiraLinha + textoUsoAcimaMeta + textoDetalheUsoAcimaMeta + dicaReducaoConsumo
				+ maisDetalhes;
	}

}
