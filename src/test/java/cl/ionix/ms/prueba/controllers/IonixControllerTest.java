package cl.ionix.ms.prueba.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cl.ionix.ms.prueba.entities.UsuarioEntity;
import cl.ionix.ms.prueba.repositories.IonixRepository;
import cl.ionix.ms.prueba.security.JwtProvider;
import cl.ionix.ms.prueba.services.impl.IonixServiceImpl;
import cl.ionix.ms.prueba.utils.IonixStub;
import cl.ionix.ms.prueba.utils.Utils;

@RunWith(SpringRunner.class)
public class IonixControllerTest {

	private MockMvc mvc;

	@Mock
	private IonixRepository repo;

	@Mock
	private JwtProvider jwtProvider;

	@InjectMocks
	private IonixController controller;
	private IonixServiceImpl serviceImpl;

	private UsuarioEntity usuarioEnty;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		serviceImpl = new IonixServiceImpl(repo);
		controller = new IonixController(serviceImpl);

		// mock
		usuarioEnty = IonixStub.generaMockListaUsuarios();

		this.mvc = MockMvcBuilders.standaloneSetup(controller).build();

	}

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	public void obtenerListaDebeResponderOK() throws Exception {

		when(repo.findAll()).thenReturn(Arrays.asList(usuarioEnty));

		mvc.perform(get("/ionix/crud").contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk());

		assertNotNull(serviceImpl.listar());

	}

	@Test
	public void updateDebeResponderOK() throws Exception {

		when(repo.save(any())).thenReturn(usuarioEnty);

		mvc.perform(put("/ionix/crud").contentType(MediaType.APPLICATION_JSON)
				.content(IonixStub.asJsonString(IonixStub.generarRequestParaModificar()))).andDo(print())
				.andExpect(status().isMovedPermanently());

		assertNotNull(serviceImpl.modifica(IonixStub.generarRequestParaModificar()));

	}

	@Test
	public void guardarDebeResponderOK() throws Exception {

		when(repo.save(any())).thenReturn(usuarioEnty);
		when(jwtProvider.getKey(any())).thenReturn("unToken");

		mvc.perform(post("/ionix/crud").headers(IonixStub.getTestHeaders()).contentType(MediaType.APPLICATION_JSON)
				.content(IonixStub.asJsonString(IonixStub.generarRequestParaGuardar()))).andDo(print())
				.andExpect(status().isOk());

		assertEquals(serviceImpl.añade(IonixStub.generarRequestParaGuardar()), Utils.generarOkMessageDTO());

	}

}
