package com.example.securingweb.model.config.security.user_details;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.securingweb.model.entity.UsuarioVO;
import com.example.securingweb.model.service.dao.Dao;
import com.example.securingweb.model.service.dao.impl.FactoryDao;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

	private Dao<UsuarioVO, String> dao = FactoryDao.criarUsuarioDao();

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsuarioVO usuario = dao.get(username).get(0);

		List<String> funcoes = new ArrayList<>();
		funcoes.add("admin");

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario não cadastrado");
		}

		boolean enabled = true;
		boolean contaNaoExpira = true;
		boolean credenciaisNaoExpira = true;
		boolean contaNaoBloqueia = true;

		return new User(usuario.getUsername(), usuario.getSenha(), enabled, contaNaoExpira, credenciaisNaoExpira,
				contaNaoBloqueia, getAuthorities(funcoes));
	}

	private static List<GrantedAuthority> getAuthorities(List<String> funcoes) {
		List<GrantedAuthority> autorizacoes = new ArrayList<>();

		for (String funcao : funcoes) {
			autorizacoes.add(new SimpleGrantedAuthority(funcao));
		}

		return autorizacoes;
	}

}
