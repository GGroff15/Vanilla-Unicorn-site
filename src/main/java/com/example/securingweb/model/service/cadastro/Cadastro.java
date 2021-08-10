package com.example.securingweb.model.service.cadastro;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securingweb.model.entity.UsuarioDTO;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.exception.UsuarioJaCadastradoException;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Service
public class Cadastro {

	Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();

	public void cadastrarNovoUsuario(UsuarioDTO novoUsuario) throws UsuarioJaCadastradoException {

		novoUsuario.setSenha(aplicarCriptografia(novoUsuario.getSenha()));

		if (emailOuUsernameExiste(novoUsuario)) {
			throw new UsuarioJaCadastradoException("Nome de usuario ou email já cadastrado!");
		}

		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.set_id(novoUsuario.get_id());
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

	public void atualizarCadastroUsuario(UsuarioVO usuarioVO) {

		dao.update(usuarioVO, usuarioVO.getUsername());
	}

	public boolean emailOuUsernameExiste(UsuarioDTO usuarioVO) {

		List<UsuarioVO> usuariosCadastrados = dao.getAll();
		for (UsuarioVO item : usuariosCadastrados) {

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
