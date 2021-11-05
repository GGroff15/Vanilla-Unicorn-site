package com.example.securingweb.model.service.consumo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

import com.example.securingweb.model.entity.ConsumoVO;
import com.example.securingweb.model.entity.IntervaloDatasVO;
import com.example.securingweb.model.entity.RelatorioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

public class Consumo {

	private static final int DIVISOR_PARA_KILO = 1000;
	private static final int NUMERO_HORAS_DO_DIA = 24;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Calendar calendar = Calendar.getInstance();

	private List<ConsumoVO> consumoPeriodo = new ArrayList<>();

	public Consumo(IntervaloDatasVO intervaloDatasVO) {
		Dao<ConsumoVO, IntervaloDatasVO> dao = FactoryDao.criarConsumoDao();
		this.consumoPeriodo = dao.get(intervaloDatasVO);
		consumoPeriodo.sort(Comparator.comparing(ConsumoVO::getData));
	}

	public List<Object> obterDadosGraficoConsumo() {
		List<Object> dadosGrafico = new ArrayList<>();
		dadosGrafico.add(Arrays.asList("Data", "Energia (kW)", "Água (L)"));
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), calcularKiloWattHora(item), item.getAgua());
			dadosGrafico.add(coluna);
		}
		return dadosGrafico;
	}

	public List<Object> obterDadosGraficoUso(Integer meta) {
		List<Object> dadosGrafico = new ArrayList<>();

		if (isMetaNula(meta)) {
			dadosGrafico.add(tituloColunasSemMeta());
			dadosUso(dadosGrafico);
		} else {
			dadosGrafico.add(tituloColunasComMeta());
			dadosUso(meta, dadosGrafico);
		}
		return dadosGrafico;
	}

	private void dadosUso(List<Object> dadosGrafico) {
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(item), calcularMediaTempoUso());
			dadosGrafico.add(coluna);
		}
	}

	private void dadosUso(Integer meta, List<Object> dadosGrafico) {
		for (ConsumoVO item : consumoPeriodo) {
			calendar.setTimeInMillis(item.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(item), calcularMediaTempoUso(), meta);
			dadosGrafico.add(coluna);
		}
	}

	private int calcularMediaTempoUso() {
		int tempoTotalPeriodo = 0;
		for (ConsumoVO consumoDia : consumoPeriodo) {
			tempoTotalPeriodo = tempoTotalPeriodo + getTempoUso(consumoDia);
		}
		return tempoTotalPeriodo / consumoPeriodo.size();
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

	private int getTempoUso(ConsumoVO item) {
		return item.getTempoUso();
	}

	private String getDataConsumo() {
		return dateFormat.format(calendar.getTime());
	}

	public List<RelatorioVO> converterDadosRelatorio(Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (ConsumoVO item : consumoPeriodo) {
			RelatorioVO relatorioVO = new RelatorioVO();

			calendar.setTimeInMillis(item.getData());

			relatorioVO.setAgua(item.getAgua());
			relatorioVO.setEnergia(item.getEnergia() / 24000);
			relatorioVO.setTempoUso(item.getTempoUso());
			if (meta != null) {
				relatorioVO.setTempoMeta(meta);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			relatorioVO.setData(simpleDateFormat.format(calendar.getTime()));

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

	private double calcularKiloWattHora(ConsumoVO item) {
		return item.getEnergia() / (NUMERO_HORAS_DO_DIA * DIVISOR_PARA_KILO);
	}

	public double calcularEnergiaPeriodo() {
		double energiaTotal = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			energiaTotal = energiaTotal + consumoVO.getEnergia();
		}
		return energiaTotal;
	}

	public double obterTotalConsumoAgua() {
		double aguaTotal = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			aguaTotal = aguaTotal + consumoVO.getAgua();
		}
		return aguaTotal;
	}

	public int calcularTotalMinutos() {
		int totalTempo = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			totalTempo = totalTempo + consumoVO.getTempoUso();
		}
		return totalTempo;
	}

	public int calcularQuantidadeDias() {
		int totalDias = 0;
		for (ConsumoVO consumoVO : consumoPeriodo) {
			if (consumoVO.getTempoUso() != 0) {
				totalDias++;
			}
		}
		return totalDias;
	}

	public List<ConsumoVO> getConsumoPeriodo() {
		return consumoPeriodo;
	}
}
