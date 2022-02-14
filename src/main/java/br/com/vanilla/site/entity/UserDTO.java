package br.com.vanilla.site.entity;

public class UserDTO {

	private String id;
	private String name;
	private String email;
	private String username;
	private String password;
	private PerfilDTO perfil = new PerfilDTO();

	public UserDTO() {
		this.id = "";
		this.name = "";
		this.username = "";
		this.email = "";
		this.password = "";
		this.perfil = new PerfilDTO();
	}

	public UserDTO(String id, String name, String email, String username, String password, PerfilDTO perfil) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.username = username;
		this.password = password;
		this.perfil = perfil;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PerfilDTO getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilDTO perfil) {
		this.perfil = perfil;
	}
}
