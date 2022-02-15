package br.com.vanilla.site.domain.entity;

public class ConsumoDTO {

	private double agua;
	private double energia;
	private int tempoUso;

	public ConsumoDTO() {
	}

	public ConsumoDTO(double agua, double energia, int tempoUso) {
		super();
		this.agua = agua;
		this.energia = energia;
		this.tempoUso = tempoUso;
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

}
