package br.com.vanilla.site.model.enums;

public enum PropriedadeUsuarioEnum {

	USERNAME("nome"), SENHA("senha"), ROLE("role"), OUTROS("outros");

	private PropriedadeUsuarioEnum(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

}
