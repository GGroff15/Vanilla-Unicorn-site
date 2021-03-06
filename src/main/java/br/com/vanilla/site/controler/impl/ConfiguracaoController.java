package br.com.vanilla.site.controler.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.service.CadastroService;

@Controller
public class ConfiguracaoController {

	private static final String ATRIBUTO_DADOS_USUARIO = "dadosUsuario";
	private static final String VIEW_CONFIGURACAO = "dados_conta";

	@Autowired
	CadastroService cadastroService;

	@GetMapping("/configuracao")
	public String carregarPagina(Model model, HttpSession session) {

		Usuario dadosUsurioLogado = (Usuario) session.getAttribute(ATRIBUTO_DADOS_USUARIO);
		model.addAttribute("usuario", dadosUsurioLogado);

		return VIEW_CONFIGURACAO;
	}

	@PostMapping("/configuracao")
	public String salvar(Model model, HttpSession session, UsuarioDTO usuario) {

		Usuario usuarioVO = new Usuario();
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
