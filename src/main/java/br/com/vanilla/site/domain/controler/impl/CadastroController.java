package br.com.vanilla.site.domain.controler.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.vanilla.site.config.event.OnRegistrationCompleteEvent;
import br.com.vanilla.site.domain.entity.UsuarioDTO;
import br.com.vanilla.site.domain.exception.UsuarioJaCadastradoException;
import br.com.vanilla.site.domain.service.CadastroService;

@Controller
public class CadastroController {

	private static final String VIEW_SIGNUP = "signup";

	private static final String ATTRIBUTE_USUARIO = "usuario";

	private ApplicationEventPublisher eventPublisher;

	private CadastroService cadastroService;

	private UsuarioDTO usuario;

	public CadastroController(ApplicationEventPublisher eventPublisher, CadastroService cadastroService) {
		this.eventPublisher = eventPublisher;
		this.cadastroService = cadastroService;
	}

	@GetMapping("/signup")
	public ModelAndView carregarPagina(Model model) {
		usuario = new UsuarioDTO();
		return new ModelAndView(VIEW_SIGNUP, ATTRIBUTE_USUARIO, usuario);
	}

	@PostMapping("/signup")
	public ModelAndView registrar(@ModelAttribute(ATTRIBUTE_USUARIO) UsuarioDTO attributeUsuario, Errors errors,
			HttpServletRequest request) {
		usuario = attributeUsuario;
		return registrarUsuario();
	}

	private ModelAndView registrarUsuario() {
		try {
			cadastroService.cadastrarNovoUsuario(usuario);
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(usuario));
			return redirecionarParaLogin();
		} catch (UsuarioJaCadastradoException | RuntimeException e) {
			return tratarException(e);
		}
	}

	private ModelAndView redirecionarParaLogin() {
		return new ModelAndView("redirect:/login", ATTRIBUTE_USUARIO, usuario);
	}

	private ModelAndView tratarException(Exception e) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put(ATTRIBUTE_USUARIO, usuario);
		modelMap.put("message", e);
		return new ModelAndView(VIEW_SIGNUP, modelMap);
	}

}
