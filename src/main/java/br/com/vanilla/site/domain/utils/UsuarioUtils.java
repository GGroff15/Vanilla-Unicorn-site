package br.com.vanilla.site.domain.utils;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import br.com.vanilla.site.domain.constants.Constants;
import br.com.vanilla.site.domain.entity.UsuarioDTO;
import br.com.vanilla.site.domain.enums.PropriedadeUsuarioEnum;

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

	public static UsuarioDTO recuperarDetalhesUsuario(HttpSession session) {
		return (UsuarioDTO) session.getAttribute(Constants.DADOS_USUARIO_SESSION);
	}

}