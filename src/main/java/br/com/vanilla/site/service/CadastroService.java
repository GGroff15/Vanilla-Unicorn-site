package br.com.vanilla.site.service;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vanilla.site.dao.Dao;
import br.com.vanilla.site.dao.impl.FactoryDao;
import br.com.vanilla.site.entity.Usuario;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.model.exception.UsuarioJaCadastradoException;

@Service
public class CadastroService {

	Dao<Usuario, String> dao = FactoryDao.criarUsuarioDao();

	public void cadastrarNovoUsuario(UsuarioDTO novoUsuario) throws UsuarioJaCadastradoException {

		novoUsuario.setSenha(aplicarCriptografia(novoUsuario.getSenha()));

		if (emailOuUsernameExiste(novoUsuario)) {
			throw new UsuarioJaCadastradoException("Nome de usuario ou email já cadastrado!");
		}

		Usuario usuarioVO = new Usuario();
		usuarioVO.setId(novoUsuario.getId());
		usuarioVO.setCelular(novoUsuario.getCelular());
		usuarioVO.setComparar(novoUsuario.isComparar());
		usuarioVO.setEmail(novoUsuario.getEmail());
		usuarioVO.setMeta(novoUsuario.getMeta());
		usuarioVO.setNome(novoUsuario.getNome());
		usuarioVO.setNotificacaoCelular(novoUsuario.isNotificacaoCelular());
		usuarioVO.setNotificacaoEmail(novoUsuario.isNotificacaoEmail());
		usuarioVO.setPotencia(novoUsuario.getPotencia());
		usuarioVO.setSenha(novoUsuario.getSenha());
		usuarioVO.setUsername(novoUsuario.getUsername());

		dao.save(usuarioVO);
	}

	public void atualizarCadastroUsuario(Usuario usuarioVO) {

		dao.update(usuarioVO, usuarioVO.getUsername());
	}

	public boolean emailOuUsernameExiste(UsuarioDTO usuarioVO) {

		List<Usuario> usuariosCadastrados = dao.getAll();
		for (Usuario item : usuariosCadastrados) {

			if ((item.getUsername().equals(usuarioVO.getUsername()))
					|| (item.getEmail().equals(usuarioVO.getEmail()))) {
				return true;
			}
		}

		return false;
	}

	private String aplicarCriptografia(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
}
