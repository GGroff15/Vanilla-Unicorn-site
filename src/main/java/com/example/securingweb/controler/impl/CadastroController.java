package com.example.securingweb.controler.impl;

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

import com.example.securingweb.controler.AbstractController;
import com.example.securingweb.model.config.event.OnRegistrationCompleteEvent;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.exception.UsuarioJaCadastradoException;
import com.example.securingweb.model.service.cadastro.Cadastro;

@Controller
public class CadastroController extends AbstractController {

	@Autowired
	ApplicationEventPublisher eventPublisher; 
	
	@Override
	@GetMapping("/signup")
	public String carregarPagina(Model model) {
		UsuarioVO usuarioVO = new UsuarioVO();
		
		model.addAttribute("usuario", usuarioVO);
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public ModelAndView registrar( @ModelAttribute("usuario") @Valid UsuarioVO usuarioVO, Errors errors, HttpServletRequest request) {
		
		Cadastro cadastro = new Cadastro();
		
		try {
			cadastro.cadastrarNovoUsuario(usuarioVO);
			
			String url = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(usuarioVO, request.getLocale(), url));
		} catch (UsuarioJaCadastradoException e) {
			ModelAndView modelAndView = new ModelAndView("signup", "usuario", usuarioVO);
			modelAndView.addObject("message", e);
			return modelAndView;
		} catch (RuntimeException e) {
			return new ModelAndView("erroEmail", "usuario", usuarioVO);
		}
		
		return new ModelAndView("redirect:/login", "usuario", usuarioVO);
	}

}
