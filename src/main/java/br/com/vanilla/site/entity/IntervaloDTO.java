package br.com.vanilla.site.entity;

import java.util.Date;

public class IntervaloDTO {

	private Date inicio;
	private Date fim;

	public IntervaloDTO() {
	}

	public IntervaloDTO(Date inicio, Date fim) {
		this.inicio = inicio;
		this.fim = fim;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

}
