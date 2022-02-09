package br.com.vanilla.site.entity;

import java.util.Date;

public class LeituraDTO {

	private String id;
	private ConsumoDTO consumo;
	private Date data;
	private String userId;

	public LeituraDTO() {
	}

	public LeituraDTO(ConsumoDTO consumo, Date data, String userId) {
		this.consumo = consumo;
		this.data = data;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ConsumoDTO getConsumo() {
		return consumo;
	}

	public void setConsumo(ConsumoDTO consumo) {
		this.consumo = consumo;
	}

}
