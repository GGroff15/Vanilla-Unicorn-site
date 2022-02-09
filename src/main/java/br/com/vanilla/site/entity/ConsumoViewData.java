package br.com.vanilla.site.entity;

import java.util.List;

public class ConsumoViewData {

	private double consumoEnergiaPeriodo;
	private int totalDias;
	private int tempoUsoTotal;
	private List<RelatorioVO> dadosRelatorio;
	private List<Object> dadosConsumo;
	private List<Object> dadosUso;
	private double consumoAgua;
	private List<LeituraDTO> consumoPeriodo;

	public void setEnergia(double consumoEnergiaPeriodo) {
		this.consumoEnergiaPeriodo = consumoEnergiaPeriodo;
	}

	public void setTotalDias(int totalDias) {
		this.totalDias = totalDias;
	}

	public void setTempoUsoTotal(int tempoUsoTotal) {
		this.tempoUsoTotal = tempoUsoTotal;
	}

	public void setRelatorio(List<RelatorioVO> dadosRelatorio) {
		this.dadosRelatorio = dadosRelatorio;
	}

	public void setDadosConsumo(List<Object> dadosConsumo) {
		this.dadosConsumo = dadosConsumo;
	}

	public void setDadosUso(List<Object> dadosUso) {
		this.dadosUso = dadosUso;
	}

	public void setAgua(double consumoAgua) {
		this.consumoAgua = consumoAgua;
	}

	public void setConsumoPeriodo(List<LeituraDTO> consumoPeriodo) {
		this.consumoPeriodo = consumoPeriodo;
	}

	public Object getDadosConsumo() {
		return this.dadosConsumo;
	}

	public Object getDadosUso() {
		return this.dadosUso;
	}

	public Object getRelatorio() {
		return this.dadosRelatorio;
	}

	public Object getAgua() {
		return this.consumoAgua;
	}

	public Object getEnergia() {
		return this.consumoEnergiaPeriodo;
	}

	public Object getTempoUsoTotal() {
		return this.tempoUsoTotal;
	}

	public Object getTotalDias() {
		return this.totalDias;
	}

	public List<LeituraDTO> getConsumoPeriodo() {
		return consumoPeriodo;
	}

}
