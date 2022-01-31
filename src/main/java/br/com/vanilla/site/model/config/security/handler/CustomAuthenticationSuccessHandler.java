package br.com.vanilla.site.model.config.security.handler;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.impl.FactoryDao;
import br.com.vanilla.site.entity.Usuario;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession();
		Dao<Usuario, String> dao = FactoryDao.criarUsuarioDao();
		
		String username = authentication.getName();
		List<Usuario> listaUsuarios = dao.get(username);
		Usuario usuario = listaUsuarios.get(0);
		session.setAttribute("dadosUsuario", usuario);
		
		redirectStrategy.sendRedirect(request, response, "/home");
	}

}
