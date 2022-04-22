package br.com.vanilla.site.data.connector;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.vanilla.site.domain.entity.IntervaloDTO;
import br.com.vanilla.site.domain.entity.LeituraDTO;
import br.com.vanilla.site.domain.entity.UsuarioDTO;

@Component
public class IntegradorConector {

	private static final String HTTP_LOCALHOST_8081_INTEGRADOR_USER = "http://localhost:8081/integrador/user";
	private RestTemplate restTemplate = new RestTemplate();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<LeituraDTO> getLeiturasIntervalo(IntervaloDTO intervalo) {
		
		String uriConsultaLeituraIntervalo = montarUrlConsultaLeituras(intervalo);
		ResponseEntity<List> response = restTemplate
				.getForEntity(uriConsultaLeituraIntervalo, List.class);
		return (List<LeituraDTO>) response.getBody();
	}

	private String montarUrlConsultaLeituras(IntervaloDTO intervalo) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		StringBuilder builder = new StringBuilder();
		builder.append("http://localhost:8081/integrador/leitura/intervalo/");
		builder.append(dateFormat.format(intervalo.getInicio()));
		builder.append("/");
		builder.append(dateFormat.format(intervalo.getFim()));
		return builder.toString();
	}

	public void saveUser(UsuarioDTO novoUsuario) {
		HttpEntity<UsuarioDTO> httpEntity = new HttpEntity<>(novoUsuario);
		restTemplate.postForEntity(HTTP_LOCALHOST_8081_INTEGRADOR_USER, httpEntity, Boolean.class);
	}

	public void atualizarUsuario(UsuarioDTO usuario) {
		HttpEntity<UsuarioDTO> httpEntity = new HttpEntity<>(usuario);
		restTemplate.put(HTTP_LOCALHOST_8081_INTEGRADOR_USER, httpEntity);
	}

	public UsuarioDTO findUserByUsername(String username) {
		ResponseEntity<UsuarioDTO> response = restTemplate
				.getForEntity(HTTP_LOCALHOST_8081_INTEGRADOR_USER + "/" + username, UsuarioDTO.class);
		return response.getBody();
	}

}