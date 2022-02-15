package br.com.vanilla.site.view.controler.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.vanilla.site.domain.entity.UsuarioDTO;
import br.com.vanilla.site.domain.service.CadastroService;

@Controller
public class ConfiguracaoController {

	private static final String ATRIBUTO_DADOS_USUARIO = "dadosUsuario";
	private static final String VIEW_CONFIGURACAO = "dados_conta";

	private CadastroService cadastroService;

	public ConfiguracaoController(CadastroService cadastroService) {
		this.cadastroService = cadastroService;
	}

	@GetMapping("/configuracao")
	public String carregarPagina(Model model, HttpSession session) {

		UsuarioDTO dadosUsurioLogado = (UsuarioDTO) session.getAttribute(ATRIBUTO_DADOS_USUARIO);
		model.addAttribute("usuario", dadosUsurioLogado);

		return VIEW_CONFIGURACAO;
	}

	@PostMapping("/configuracao")
	public String salvar(Model model, HttpSession session, UsuarioDTO usuario) {

		UsuarioDTO usuarioVO = new UsuarioDTO();
		usuarioVO.setId(usuario.getId());
		usuarioVO.setCelular(usuario.getCelular());
		usuarioVO.setComparar(usuario.isComparar());
		usuarioVO.setEmail(usuario.getEmail());
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
