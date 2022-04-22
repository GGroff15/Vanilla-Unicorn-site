package br.com.vanilla.site.domain.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import br.com.vanilla.site.data.connector.IntegradorConector;
import br.com.vanilla.site.domain.entity.UsuarioDTO;
import br.com.vanilla.site.domain.exception.UsuarioJaCadastradoException;

class CadastroServiceTest {
	

	@Test
	void testCadastrarNovoUsuario() throws UsuarioJaCadastradoException {
		IntegradorConector integrador = mock(IntegradorConector.class);
		
		when(integrador.findUserByUsername("junit")).thenReturn(new UsuarioDTO());
		
		CadastroService cadastroService = new CadastroService(integrador );
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setCelular(0);
		usuario.setComparar(false);
		usuario.setEmail("junit@junit");
		usuario.setMeta(0);
		usuario.setNome("junit");
		usuario.setSenha("123");
		usuario.setUsername("junit");
		cadastroService.cadastrarNovoUsuario(usuario);
		
		verify(integrador).saveUser(usuario);
	}

}
