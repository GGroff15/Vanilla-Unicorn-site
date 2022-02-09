package br.com.vanilla.site.connector;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.vanilla.site.entity.IntervaloDTO;
import br.com.vanilla.site.entity.LeituraDTO;
import br.com.vanilla.site.entity.UsuarioDTO;

@Component
public class IntegradorConector {

	private RestTemplate restTemplate;

	public IntegradorConector() {
		restTemplate = new RestTemplate();
	}

	@SuppressWarnings("unchecked")
	public List<LeituraDTO> getLeiturasIntervalo(IntervaloDTO intervalo) {
		HttpEntity<IntervaloDTO> httpEntity = new HttpEntity<>(intervalo);
		return (List<LeituraDTO>) restTemplate.exchange("http://localhost:8081/integrador/leitura/intervalo",
				HttpMethod.GET, httpEntity, List.class);
	}

	public void saveUser(UsuarioDTO novoUsuario) {
		HttpEntity<UsuarioDTO> httpEntity = new HttpEntity<>(novoUsuario);
		restTemplate.postForEntity("http://localhost:8081/integrador/user", httpEntity, Boolean.class);
	}

	public void atualizarUsuario(UsuarioDTO usuario) {
		HttpEntity<UsuarioDTO> httpEntity = new HttpEntity<>(usuario);
		restTemplate.put("http://localhost:8081/integrador/user", httpEntity);
	}

	public UsuarioDTO findUserByUsername(String username) {
		ResponseEntity<UsuarioDTO> response = restTemplate
				.getForEntity("http://localhost:8081/integrador/user/" + username, UsuarioDTO.class);
		return response.getBody();
	}

	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> listarUsuarios() {
		return (List<UsuarioDTO>) restTemplate.getForEntity("http://localhost:8081/integrador/user", List.class);
	}

}
