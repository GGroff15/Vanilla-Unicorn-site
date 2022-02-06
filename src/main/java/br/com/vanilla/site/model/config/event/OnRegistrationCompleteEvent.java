package br.com.vanilla.site.model.config.event;

import org.springframework.context.ApplicationEvent;

import br.com.vanilla.site.entity.UsuarioDTO;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3442130126662917071L;

	public OnRegistrationCompleteEvent(UsuarioDTO usuarioVO) {
		super(usuarioVO);
	}

}
