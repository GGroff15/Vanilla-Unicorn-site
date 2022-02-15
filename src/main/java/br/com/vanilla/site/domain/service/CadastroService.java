package br.com.vanilla.site.domain.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.vanilla.site.data.connector.IntegradorConector;
import br.com.vanilla.site.domain.entity.UsuarioDTO;
import br.com.vanilla.site.domain.exception.UsuarioJaCadastradoException;

@Service
public class CadastroService {

	private IntegradorConector integradorConector;

	public CadastroService(IntegradorConector integradorConector) {
		this.integradorConector = integradorConector;
	}

	public void cadastrarNovoUsuario(UsuarioDTO novoUsuario) throws UsuarioJaCadastradoException {
		novoUsuario.setSenha(aplicarCriptografia(novoUsuario.getSenha()));
		emailOuUsernameExiste(novoUsuario);
		integradorConector.saveUser(novoUsuario);
	}

	public void atualizarCadastroUsuario(UsuarioDTO usuario) {
		integradorConector.atualizarUsuario(usuario);
	}

	public void emailOuUsernameExiste(UsuarioDTO usuario) throws UsuarioJaCadastradoException {
		UsuarioDTO userEncontrado = integradorConector.findUserByUsername(usuario.getUsername());
		if (userEncontrado.equals(usuario)) {
			throw new UsuarioJaCadastradoException("Nome de usuario ou email já cadastrado!");
		}
	}

	private String aplicarCriptografia(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
}
