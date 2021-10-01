package com.example.securingweb.model.config.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.example.securingweb.model.entity.UsuarioDTO;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3442130126662917071L;
	private String url;
	private Locale local;
	private UsuarioDTO usuarioVO;

	public OnRegistrationCompleteEvent(UsuarioDTO usuarioVO, Locale local, String url) {
		super(usuarioVO);

		this.usuarioVO = usuarioVO;
		this.local = local;
		this.url = url;
	}

}
