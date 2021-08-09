package com.example.securingweb.controler.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.controler.AbstractController;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.cadastro.Cadastro;

@Controller
public class ConfiguracaoController extends AbstractController {

	@Autowired
	Cadastro cadastroService;

	@Override
	public String carregarPagina(Model model) {
		return "configuracao";
	}
	
	@GetMapping("/configuracao")
	public String carregarPagina(Model model, HttpSession session) {
		
		UsuarioVO dadosUsurioLogado = (UsuarioVO) session.getAttribute("dadosUsuario");
		model.addAttribute("usuario", dadosUsurioLogado);
		
		return "configuracao";
	}
	
	@PostMapping("/configuracao")
	public String salvar(Model model, HttpSession session, UsuarioVO usuarioVO) {
		
		UsuarioVO dadosUsurioLogado = (UsuarioVO) session.getAttribute("dadosUsuario");
		usuarioVO.setSenha(dadosUsurioLogado.getSenha());
		cadastroService.atualizarCadastroUsuario(usuarioVO);
		session.setAttribute("dadosUsuario", usuarioVO);
		model.addAttribute("usuario", usuarioVO);
		
		return "configuracao";
	}

}
