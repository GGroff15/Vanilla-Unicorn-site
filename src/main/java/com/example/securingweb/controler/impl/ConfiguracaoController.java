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
public class ConfiguracaoController implements AbstractController {

	private static final String ATRIBUTO_DADOS_USUARIO = "dadosUsuario";
	private static final String VIEW_CONFIGURACAO = "configuracao";
	@Autowired
	Cadastro cadastroService;

	@Override
	public String carregarPagina(Model model) {
		return VIEW_CONFIGURACAO;
	}

	@GetMapping("/configuracao")
	public String carregarPagina(Model model, HttpSession session) {

		UsuarioVO dadosUsurioLogado = (UsuarioVO) session.getAttribute(ATRIBUTO_DADOS_USUARIO);
		model.addAttribute("usuario", dadosUsurioLogado);

		return VIEW_CONFIGURACAO;
	}

	@PostMapping("/configuracao")
	public String salvar(Model model, HttpSession session, UsuarioVO usuarioVO) {

		UsuarioVO dadosUsurioLogado = (UsuarioVO) session.getAttribute(ATRIBUTO_DADOS_USUARIO);
		usuarioVO.setSenha(dadosUsurioLogado.getSenha());
		cadastroService.atualizarCadastroUsuario(usuarioVO);
		session.setAttribute(ATRIBUTO_DADOS_USUARIO, usuarioVO);
		model.addAttribute("usuario", usuarioVO);

		return VIEW_CONFIGURACAO;
	}

}
