package com.example.securingweb.model.service.consumo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Component
public class Consumo {

	private static final int DIVISOR_PARA_KILO = 1000;
	private static final int NUMERO_HORAS_DO_DIA = 24;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Calendar calendar = Calendar.getInstance();

	public List<ConsumoVO> consultar(IntervaloDatasVO intervaloDatasVO) {

		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();

		return dao.get(intervaloDatasVO);
	}

	public List<Object> converterDadosGraficoConsumo(List<ConsumoVO> consumoPeriodo) {
		List<Object> dadosGrafico = new ArrayList<>();
		dadosGrafico.add(Arrays.asList("Data", "Energia (kW)", "Água (L)"));

		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), calcularKiloWattHora(item), item.getAgua());

			dadosGrafico.add(coluna);
		}

		return dadosGrafico;
	}

	public List<Object> converterDadosGraficoUso(List<ConsumoVO> consumoPeriodo, Integer meta) {
		List<Object> dadosGrafico = new ArrayList<>();

		if (isMetaNula(meta)) {
			dadosGrafico.add(tituloColunasSemMeta());
			dadosUsoSemMeta(consumoPeriodo, dadosGrafico);
		} else {
			dadosGrafico.add(tituloColunasComMeta());
			dadosUsoComMeta(consumoPeriodo, meta, dadosGrafico);
		}
		return dadosGrafico;
	}

	private void dadosUsoSemMeta(List<ConsumoVO> consumoPeriodo, List<Object> dadosGrafico) {
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(item),
					calcularMediaTempoUso(consumoPeriodo));
			dadosGrafico.add(coluna);
		}
	}

	private void dadosUsoComMeta(List<ConsumoVO> consumoPeriodo, Integer meta, List<Object> dadosGrafico) {
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(item),
					calcularMediaTempoUso(consumoPeriodo), meta);
			dadosGrafico.add(coluna);
		}
	}

	private List<String> tituloColunasComMeta() {
		return Arrays.asList("Data", "Real", "Media", "Meta");
	}

	private List<String> tituloColunasSemMeta() {
		return Arrays.asList("Data", "Real", "Media");
	}

	private boolean isMetaNula(Integer meta) {
		return meta == null || meta == 0;
	}

	private int calcularMediaTempoUso(List<ConsumoVO> consumoPeriodo) {
		int tempoTotalPeriodo = 0;
		for (ConsumoVO consumoDia : consumoPeriodo) {
			tempoTotalPeriodo = tempoTotalPeriodo + getTempoUso(consumoDia);
		}
		int mediaTempoUso = tempoTotalPeriodo / consumoPeriodo.size();
		return mediaTempoUso;
	}

	private int getTempoUso(ConsumoVO item) {
		return item.getTempoUso();
	}

	private String getDataConsumo() {
		return dateFormat.format(calendar.getTime());
	}

	public List<RelatorioVO> converterDadosRelatorio(List<ConsumoVO> consumoPeriodo, Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (ConsumoVO item : consumoPeriodo) {
			RelatorioVO relatorioVO = new RelatorioVO();

			calendar.setTimeInMillis(item.getData());

			relatorioVO.setAgua(item.getAgua());
			relatorioVO.setEnergia(calcularKiloWattHora(item));
			relatorioVO.setTempoUso(getTempoUso(item));
			if (meta != null) {
				relatorioVO.setTempoMeta(meta); // Obter esse valor dinamicamente com base no usuario logado
			}
			relatorioVO.setData(getDataConsumo());

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

	private double calcularKiloWattHora(ConsumoVO item) {
		return item.getEnergia() / (NUMERO_HORAS_DO_DIA * DIVISOR_PARA_KILO);
	}

	public double calcularEnergiaPeriodo(List<ConsumoVO> consumoPeriodo) {
		double energiaTotal = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			energiaTotal = energiaTotal + consumoVO.getEnergia();
		}
		return energiaTotal;
	}

	public double calcularAguaPeriodo(List<ConsumoVO> consumoPeriodo) {
		double aguaTotal = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			aguaTotal = aguaTotal + consumoVO.getAgua();
		}
		return aguaTotal;
	}

	public int calcularTotalMinutos(List<ConsumoVO> consumoPeriodo) {
		int totalTempo = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			totalTempo = totalTempo + consumoVO.getTempoUso();
		}
		return totalTempo;
	}

	public int calcularQuantidadeDias(List<ConsumoVO> consumoPeriodo) {
		int totalDias = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			if (consumoVO.getTempoUso() != 0) {
				totalDias++;
			}
		}
		return totalDias;
	}
}
