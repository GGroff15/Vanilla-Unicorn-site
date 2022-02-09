package br.com.vanilla.site.entity;

public class FiltroRelatorio {

	private IntervaloDTO intervalo = new IntervaloDTO();
	private String username;

	public IntervaloDTO getIntervaloDatasVO() {
		return intervalo;
	}

	public void setIntervaloDatasVO(IntervaloDTO intervalo) {
		this.intervalo = intervalo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
