package br.com.vanilla.site.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;

public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5976220206002467857L;

	private ObjectId id;

	private String nome;

	private String username;

	private String senha;

	private String email;

	private long potencia;

	private long celular;

	private boolean notificacaoCelular;

	private boolean notificacaoEmail;

	private boolean comparar;

	private int meta;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPotencia() {
		return potencia;
	}

	public void setPotencia(long potencia) {
		this.potencia = potencia;
	}

	public long getCelular() {
		return celular;
	}

	public void setCelular(long celular) {
		this.celular = celular;
	}

	public boolean isComparar() {
		return comparar;
	}

	public void setComparar(boolean comparar) {
		this.comparar = comparar;
	}

	public int getMeta() {
		return meta;
	}

	public void setMeta(int meta) {
		this.meta = meta;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public boolean isNotificacaoCelular() {
		return notificacaoCelular;
	}

	public void setNotificacaoCelular(boolean notificacaoCelular) {
		this.notificacaoCelular = notificacaoCelular;
	}

	public boolean isNotificacaoEmail() {
		return notificacaoEmail;
	}

	public void setNotificacaoEmail(boolean notificacaoEmail) {
		this.notificacaoEmail = notificacaoEmail;
	}

}
