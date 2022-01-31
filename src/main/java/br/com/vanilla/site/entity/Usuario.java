package br.com.vanilla.site.entity;

import java.io.Serializable;
import java.util.Objects;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.vanilla.site.model.constants.Constants;

@Component
@Document(collection = "clientes")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5976220206002467857L;

	@Id
	private ObjectId id;

	private String nome;

	private String username;

	private String senha;

	private String email;

	private String permissao;

	private long potencia;

	private long celular;

	private boolean notificacaoCelular;

	private boolean notificacaoEmail;

	private boolean comparar;

	private int meta;

	private boolean habilitado;

	public Usuario() {
		super();
		this.habilitado = false;
	}

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

	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();

		node.put(Constants.ID, getId().toString());
		node.put(Constants.USUARIO_NOME, this.nome);
		node.put(Constants.USUARIO_USERNAME, this.username);
		node.put(Constants.USUARIO_SENHA, this.senha);
		node.put(Constants.USUARIO_POTENCIA, this.potencia);
		node.put(Constants.USUARIO_EMAIL, this.email);
		node.put(Constants.USUARIO_CELULAR, Objects.toString(this.celular, "celular nao cadastrado"));
		node.put(Constants.USUARIO_COMPARAR, Objects.toString(this.comparar, "nao comparar consumo"));
		node.put(Constants.USUARIO_META, Objects.toString(this.meta, "meta não definida"));

		String json = "";
		try {
			json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
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

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	protected String getPermissao() {
		return permissao;
	}

	protected void setPermissao(String permissao) {
		this.permissao = permissao;
	}

}
