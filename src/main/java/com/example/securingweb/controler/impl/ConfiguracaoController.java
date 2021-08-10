package com.example.securingweb.controler.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.securingweb.controler.AbstractController;
import com.example.securingweb.model.entity.UsuarioDTO;
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
	public String salvar(Model model, HttpSession session, UsuarioDTO usuario) {

		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.set_id(usuario.get_id());
		usuarioVO.setCelular(usuario.getCelular());
		usuarioVO.setComparar(usuario.isComparar());
		usuarioVO.setEmail(usuario.getEmail());
		usuarioVO.setHabilitado(usuario.isHabilitado());
		usuarioVO.setMeta(usuario.getMeta());
		usuarioVO.setNome(usuario.getNome());
		usuarioVO.setNotificacaoCelular(usuario.isNotificacaoCelular());
		usuarioVO.setNotificacaoEmail(usuario.isNotificacaoEmail());
		usuarioVO.setPotencia(usuario.getPotencia());
		usuarioVO.setSenha(usuario.getSenha());
		usuarioVO.setUsername(usuario.getUsername());

		UsuarioDTO dadosUsurioLogado = (UsuarioDTO) session.getAttribute(ATRIBUTO_DADOS_USUARIO);
		usuario.setSenha(dadosUsurioLogado.getSenha());
		cadastroService.atualizarCadastroUsuario(usuarioVO);
		session.setAttribute(ATRIBUTO_DADOS_USUARIO, usuario);
		model.addAttribute("usuario", usuario);

		return VIEW_CONFIGURACAO;
	}

}
