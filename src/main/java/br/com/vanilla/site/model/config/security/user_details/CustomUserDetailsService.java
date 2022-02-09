package br.com.vanilla.site.model.config.security.user_details;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.vanilla.site.connector.IntegradorConector;
import br.com.vanilla.site.entity.UsuarioDTO;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private IntegradorConector integradorConector;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsuarioDTO usuario = integradorConector.findUserByUsername(username);

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
