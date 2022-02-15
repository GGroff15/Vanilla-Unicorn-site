package br.com.vanilla.site.domain.entity;

public class PerfilDTO {

	private String id;
	private String nome;

	public PerfilDTO() {
		this.id = "";
		this.nome = "";
	}

	public PerfilDTO(String id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
