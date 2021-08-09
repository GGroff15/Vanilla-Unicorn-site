package com.example.securingweb.model.entity;

public class RelatorioVO {

	private String data = "";
	private double agua = 0;
	private double energia = 0;
	private int tempoUso = 0;
	private int tempoMeta = 0;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getAgua() {
		return agua;
	}

	public void setAgua(double agua) {
		this.agua = agua;
	}

	public double getEnergia() {
		return energia;
	}

	public void setEnergia(double energia) {
		this.energia = energia;
	}

	public int getTempoUso() {
		return tempoUso;
	}

	public void setTempoUso(int tempoUso) {
		this.tempoUso = tempoUso;
	}

	public int getTempoMeta() {
		return tempoMeta;
	}

	public void setTempoMeta(int tempoMeta) {
		this.tempoMeta = tempoMeta;
	}

}
