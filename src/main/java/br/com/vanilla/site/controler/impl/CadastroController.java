package br.com.vanilla.site.controler.impl;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.model.config.event.OnRegistrationCompleteEvent;
import br.com.vanilla.site.model.exception.UsuarioJaCadastradoException;
import br.com.vanilla.site.service.CadastroService;

@Controller
public class CadastroController {

	private static final String USUARIO = "usuario";
	@Autowired
	ApplicationEventPublisher eventPublisher;

	@GetMapping("/signup")
	public String carregarPagina(Model model) {
		Usuario usuarioVO = new Usuario();

		model.addAttribute(USUARIO, usuarioVO);

		return "signup";
	}

	@PostMapping("/signup")
	public ModelAndView registrar(@ModelAttribute(USUARIO) @Valid UsuarioDTO usuario, Errors errors,
			HttpServletRequest request) {

		CadastroService cadastro = new CadastroService();

		try {
			cadastro.cadastrarNovoUsuario(usuario);

			String url = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(usuario, request.getLocale(), url));
		} catch (UsuarioJaCadastradoException e) {
			ModelAndView modelAndView = new ModelAndView("signup", USUARIO, usuario);
			modelAndView.addObject("message", e);
			return modelAndView;
		} catch (RuntimeException e) {
			return new ModelAndView("erroEmail", USUARIO, usuario);
		}

		return new ModelAndView("redirect:/login", USUARIO, usuario);
	}

}
