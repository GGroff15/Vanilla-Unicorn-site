package com.example.securingweb.model.config.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.example.securingweb.model.entity.UsuarioVO;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3442130126662917071L;
	private String url;
	private Locale local;
	private UsuarioVO usuarioVO;

	public OnRegistrationCompleteEvent(UsuarioVO usuarioVO, Locale local, String url) {
		super(usuarioVO);

		this.usuarioVO = usuarioVO;
		this.local = local;
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Locale getLocal() {
		return local;
	}

	public void setLocal(Locale local) {
		this.local = local;
	}

	public UsuarioVO getUsuarioVO() {
		return usuarioVO;
	}

	public void setUsuarioVO(UsuarioVO usuarioVO) {
		this.usuarioVO = usuarioVO;
	}

}
