package br.com.vanilla.site.entity;

import java.util.List;

public class Consumo {

	private double energia;
	private double agua;
	private int tempoUsoTotal;
	private List<Object> dadosConsumo;
	private List<Object> dadosUso;
	private List<RelatorioVO> relatorio;
	private int totalDias;
	private List<ConsumoVO> consumoPeriodo;

	public void setEnergia(double energia) {
		this.energia = energia;
	}

	public void setAgua(double agua) {
		this.agua = agua;
	}

	public void setTempoUsoTotal(int tempoUsoTotal) {
		this.tempoUsoTotal = tempoUsoTotal;
	}

	public void setDadosConsumo(List<Object> dadosConsumo) {
		this.dadosConsumo = dadosConsumo;
	}

	public void setDadosUso(List<Object> dadosUso) {
		this.dadosUso = dadosUso;
	}

	public void setTotalDias(int totalDias) {
		this.totalDias = totalDias;
	}

	public void setRelatorio(List<RelatorioVO> relatorio) {
		this.relatorio = relatorio;
	}

	public double getEnergia() {
		return energia;
	}

	public double getAgua() {
		return agua;
	}

	public int getTempoUsoTotal() {
		return tempoUsoTotal;
	}

	public List<Object> getDadosConsumo() {
		return dadosConsumo;
	}

	public List<Object> getDadosUso() {
		return dadosUso;
	}

	public List<RelatorioVO> getRelatorio() {
		return relatorio;
	}

	public int getTotalDias() {
		return totalDias;
	}

	public void setConsumoPeriodo(List<ConsumoVO> consumoPeriodo) {
		this.consumoPeriodo = consumoPeriodo;
	}

	public List<ConsumoVO> getConsumoPeriodo() {
		return consumoPeriodo;
	}

}
