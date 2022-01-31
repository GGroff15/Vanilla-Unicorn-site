package br.com.vanilla.site.utils;


import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.model.constants.Constants;
import br.com.vanilla.site.model.enums.PropriedadeUsuarioEnum;

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

	public static Usuario recuperarDetalhesUsuario(HttpSession session) {
		return (Usuario) session.getAttribute(Constants.DADOS_USUARIO_SESSION);
	}

}
