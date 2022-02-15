package br.com.vanilla.site.domain.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.vanilla.site.data.connector.IntegradorConector;
import br.com.vanilla.site.domain.entity.ConsumoDTO;
import br.com.vanilla.site.domain.entity.IntervaloDTO;
import br.com.vanilla.site.domain.entity.LeituraDTO;
import br.com.vanilla.site.domain.entity.RelatorioVO;

@Service
public class ConsumoService {

	private static final int DIVISOR_PARA_KILO = 1000;
	private static final int NUMERO_HORAS_DO_DIA = 24;

	private IntegradorConector integradorConector;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Calendar calendar = Calendar.getInstance();

	private List<LeituraDTO> leiturasPeriodo;

	public ConsumoService(IntegradorConector integradorConector, List<LeituraDTO> leiturasPeriodo) {
		this.integradorConector = integradorConector;
		this.leiturasPeriodo = leiturasPeriodo;
	}

	public void setIntervalo(IntervaloDTO intervalo) {
		leiturasPeriodo = integradorConector.getLeiturasIntervalo(intervalo);
	}

	public List<Object> obterDadosConsumo() {
		List<Object> dadosGrafico = new ArrayList<>();
		dadosGrafico.add(Arrays.asList("Data", "Energia (kW)", "Água (L)"));
		for (LeituraDTO leitura : leiturasPeriodo) {
			calendar.setTime(leitura.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), calcularKiloWattHora(leitura.getConsumo()),
					leitura.getConsumo().getAgua());
			dadosGrafico.add(coluna);
		}
		return dadosGrafico;
	}

	public List<Object> obterDadosUso(Integer meta) {
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
		for (LeituraDTO leitura : leiturasPeriodo) {
			calendar.setTime(leitura.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(leitura), calcularMediaTempoUso());
			dadosGrafico.add(coluna);
		}
	}

	private void dadosUso(Integer meta, List<Object> dadosGrafico) {
		for (LeituraDTO leitura : leiturasPeriodo) {
			calendar.setTime(leitura.getData());
			List<Object> coluna = Arrays.asList(getDataConsumo(), getTempoUso(leitura), calcularMediaTempoUso(), meta);
			dadosGrafico.add(coluna);
		}
	}

	private int calcularMediaTempoUso() {
		int tempoTotalPeriodo = 0;
		for (LeituraDTO leitura : leiturasPeriodo) {
			tempoTotalPeriodo = tempoTotalPeriodo + getTempoUso(leitura);
		}
		return tempoTotalPeriodo / leiturasPeriodo.size();
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

	private int getTempoUso(LeituraDTO leitura) {
		return leitura.getConsumo().getTempoUso();
	}

	private String getDataConsumo() {
		return dateFormat.format(calendar.getTime());
	}

	public List<RelatorioVO> obterDadosRelatorio(Integer meta) {
		List<RelatorioVO> dadosRelatorio = new ArrayList<>();

		for (LeituraDTO leitura : leiturasPeriodo) {
			RelatorioVO relatorioVO = new RelatorioVO();

			calendar.setTime(leitura.getData());

			ConsumoDTO consumo = leitura.getConsumo();

			relatorioVO.setAgua(consumo.getAgua());
			relatorioVO.setEnergia(consumo.getEnergia() / 24000);
			relatorioVO.setTempoUso(consumo.getTempoUso());
			if (meta != null) {
				relatorioVO.setTempoMeta(meta);
			}
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			relatorioVO.setData(simpleDateFormat.format(calendar.getTime()));

			dadosRelatorio.add(relatorioVO);
		}

		return dadosRelatorio;
	}

	private double calcularKiloWattHora(ConsumoDTO item) {
		return item.getEnergia() / (NUMERO_HORAS_DO_DIA * DIVISOR_PARA_KILO);
	}

	public double consumoEnergiaPeriodo() {
		double energiaTotal = 0;
		for (LeituraDTO leitura : leiturasPeriodo) {
			energiaTotal = energiaTotal + leitura.getConsumo().getEnergia();
		}
		return energiaTotal;
	}

	public double obterTotalConsumoAgua() {
		double aguaTotal = 0;
		for (LeituraDTO leitura : leiturasPeriodo) {
			aguaTotal = aguaTotal + leitura.getConsumo().getAgua();
		}
		return aguaTotal;
	}

	public int calcularTempoUsoTotal() {
		int totalTempo = 0;
		for (LeituraDTO leitura : leiturasPeriodo) {
			totalTempo = totalTempo + leitura.getConsumo().getTempoUso();
		}
		return totalTempo;
	}

	public int calcularQuantidadeDias() {
		int totalDias = 0;
		for (LeituraDTO leitura : leiturasPeriodo) {
			if (leitura.getConsumo().getTempoUso() != 0) {
				totalDias++;
			}
		}
		return totalDias;
	}

	public List<LeituraDTO> getConsumoPeriodo() {
		return leiturasPeriodo;
	}
}
