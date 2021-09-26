package com.example.securingweb.utils;

import static com.example.securingweb.model.constants.Constants.DADOS_USUARIO_SESSION;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.example.securingweb.model.entity.UsuarioVO;
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

	public static UsuarioVO recuperarDetalhesUsuario(HttpSession session) {
		return (UsuarioVO) session.getAttribute(DADOS_USUARIO_SESSION);
	}

}
