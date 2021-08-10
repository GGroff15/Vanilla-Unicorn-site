package com.example.securingweb.model.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.example.securingweb.model.enums.PropriedadeUsuarioEnum;

public class UsuarioUtils {

	private UsuarioUtils() {
	}

	public static String recuperarDadoUsuario(String propriedade) {

		if (PropriedadeUsuarioEnum.USERNAME.getDescricao().equals(propriedade)) {
			User username = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return username.getUsername();

		} else if (PropriedadeUsuarioEnum.SENHA.getDescricao().equals(propriedade)) {
			User username = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return username.getPassword();

		}
		return "";
	}

}
