package br.com.vanilla.site.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.vanilla.site.data.connector.IntegradorConector;
import br.com.vanilla.site.domain.entity.UsuarioDTO;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private IntegradorConector integradorConector;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		HttpSession session = request.getSession();

		String username = authentication.getName();
		UsuarioDTO usuario = integradorConector.findUserByUsername(username);
		session.setAttribute("dadosUsuario", usuario);

		redirectStrategy.sendRedirect(request, response, "/home");
	}

}
