package com.example.securingweb.model.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.Id;

public class TokenVerificacao {

	private static final int EXPIRACAO = 60 * 24;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = UsuarioVO.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private UsuarioVO usuario;
	
	private Date dataExpiracao;
	
	public TokenVerificacao(String token, UsuarioVO usuarioVO) {
		this.usuario = usuarioVO;
		this.token = token;
	}

	private Date calcularDataExpiracao(int horaExpiracaoMinutos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, horaExpiracaoMinutos);
		return new Date(calendar.getTime().getTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioVO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioVO usuario) {
		this.usuario = usuario;
	}

	public Date getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public static int getExpiracao() {
		return EXPIRACAO;
	}
}
