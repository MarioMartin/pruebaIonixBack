package cl.ionix.ms.prueba.utils;

import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import cl.ionix.ms.prueba.dtos.CreaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.ModificaUsuarioDTO;
import cl.ionix.ms.prueba.entities.UsuarioEntity;

public class IonixStub {

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static UsuarioEntity generaMockListaUsuarios() {

		UsuarioEntity entity = new UsuarioEntity();

		entity.setId(1L);
		entity.setUsername("Rick");
		entity.setLastName("Bowie");
		entity.setEmail("mhernandez@gmail.com");
		entity.setPassword("w#e45");
		entity.setFirstName("mario");

		return entity;
	}

	public static CreaUsuarioDTO generarRequestParaGuardar() {
		CreaUsuarioDTO request = new CreaUsuarioDTO();
		request.setUsername("Rick");
		request.setLastName("Bowie");
		request.setEmail("mhernandez@gmail.com");
		request.setPassword("w#e45");
		request.setFirstName("mario");

		return request;
	}

	public static ModificaUsuarioDTO generarRequestParaModificar() {
		ModificaUsuarioDTO request = new ModificaUsuarioDTO();
		request.setId(1L);
		request.setUsername("Rick");
		request.setLastName("Bowie");
		request.setEmail("mhernandez@gmail.com");
		request.setPassword("w#e45");
		request.setFirstName("mario");
		return request;
	}

	public static HttpHeaders getTestHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "unToken");
		return headers;
	}

}
