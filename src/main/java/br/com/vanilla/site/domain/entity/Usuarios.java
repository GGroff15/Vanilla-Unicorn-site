package br.com.vanilla.site.domain.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Usuarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8078987631650244547L;

	private List<UserDTO> usuarioDTOs = new ArrayList<>();

	public List<UserDTO> getUsuarioDTOs() {
		return usuarioDTOs;
	}

	public void setUsuarioDTOs(List<UserDTO> usuarioDTOs) {
		this.usuarioDTOs = usuarioDTOs;
	}

}
