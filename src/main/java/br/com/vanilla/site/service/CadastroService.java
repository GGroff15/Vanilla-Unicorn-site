package br.com.vanilla.site.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vanilla.site.connector.IntegradorConector;
import br.com.vanilla.site.entity.UsuarioDTO;
import br.com.vanilla.site.model.exception.UsuarioJaCadastradoException;

@Service
public class CadastroService {
	
	@Autowired
	private IntegradorConector integradorConector;

	public void cadastrarNovoUsuario(UsuarioDTO novoUsuario) throws UsuarioJaCadastradoException {

		novoUsuario.setSenha(aplicarCriptografia(novoUsuario.getSenha()));

		if (emailOuUsernameExiste(novoUsuario)) {
			throw new UsuarioJaCadastradoException("Nome de usuario ou email já cadastrado!");
		}
		
		integradorConector.saveUser(novoUsuario);
	}

	public void atualizarCadastroUsuario(UsuarioDTO usuario) {

		dao.update(usuario, usuario.getUsername());
	}

	public boolean emailOuUsernameExiste(UsuarioDTO usuarioVO) {

		List<UsuarioDTO> usuariosCadastrados = dao.getAll();
		for (UsuarioDTO usuario : usuariosCadastrados) {

			if ((usuario.getUsername().equals(usuarioVO.getUsername()))
					|| (usuario.getEmail().equals(usuarioVO.getEmail()))) {
				return true;
			}
		}

		return false;
	}

	private String aplicarCriptografia(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
}
