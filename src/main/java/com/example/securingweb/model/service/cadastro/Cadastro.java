package com.example.securingweb.model.service.cadastro;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securingweb.model.entity.TokenVerificacao;
import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.exception.UsuarioJaCadastradoException;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Service
public class Cadastro {
	
	Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();

	public void cadastrarNovoUsuario(UsuarioVO novoUsuario) throws UsuarioJaCadastradoException {
		
		novoUsuario.setSenha(aplicarCriptografia(novoUsuario.getSenha()));
		
		if (emailOuUsernameExiste(novoUsuario)) {
			throw new UsuarioJaCadastradoException("Nome de usuario ou email já cadastrado!");
		}
		
		dao.save(novoUsuario);
	}
	
	public void atualizarCadastroUsuario(UsuarioVO usuarioVO) {
		
		Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();
		dao.update(usuarioVO, usuarioVO.getUsername());
	}
	
	public boolean emailOuUsernameExiste(UsuarioVO usuarioVO) {
		
		List<UsuarioVO> usuariosCadastrados = dao.getAll();
		for (UsuarioVO item : usuariosCadastrados) {
			
			if ((item.getUsername().equals(usuarioVO.getUsername())) || (item.getEmail().equals(usuarioVO.getEmail()))) {
				return true;
			}
		}
		
		return false;
	}
	
	private String aplicarCriptografia(String senha) {
		return new BCryptPasswordEncoder().encode(senha);
	}
	
	public void createVerificationTokenForUser(final UsuarioVO usuarioVO, final String token) {
        final TokenVerificacao myToken = new TokenVerificacao(token, usuarioVO);
    }
}
