package cl.ionix.ms.prueba.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cl.ionix.ms.prueba.dtos.CreaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.ListUsuariosResponse;
import cl.ionix.ms.prueba.dtos.LoginRequest;
import cl.ionix.ms.prueba.dtos.MessageDTO;
import cl.ionix.ms.prueba.dtos.ModificaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.UsuarioDTO;
import cl.ionix.ms.prueba.exceptions.IonixException;
import cl.ionix.ms.prueba.security.JwtProvider;
import cl.ionix.ms.prueba.services.IonixService;
import cl.ionix.ms.prueba.utils.Constantes;
import cl.ionix.ms.prueba.utils.HeadersUtils;
import cl.ionix.ms.prueba.utils.Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/ionix/crud")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api(tags = { "Crud Test para Ionix" })
public class IonixController {

	@Autowired
	private JwtProvider jwtProvider;

	private final IonixService service;

	@Autowired
	public IonixController(final IonixService service) {
		this.service = service;
	}

	@RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Retorna lista de usuarios", response = ListUsuariosResponse.class,
			responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<ListUsuariosResponse> listar() throws IonixException {

		ListUsuariosResponse response = service.listar();
		return new ResponseEntity<ListUsuariosResponse>(response, HeadersUtils.getGenericHeaders(), HttpStatus.OK);

	}

	@RequestMapping(path = "", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Agregar usuario", response = UsuarioDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<UsuarioDTO> añade(@RequestHeader(value = "Authorization") String token,
			@Valid @RequestBody CreaUsuarioDTO req) throws IonixException {

		if (!validarToken(token)) {
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(), HeadersUtils.getGenericHeaders(), HttpStatus.OK);
		}

		UsuarioDTO response = service.añade(req);
		return new ResponseEntity<UsuarioDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(path = "/usuario/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Obtiene Usuario", response = UsuarioDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<UsuarioDTO> obtieneUsuario(@Valid @PathVariable("id") Long id) throws IonixException {

		UsuarioDTO response = service.obtieneUsuario(id);

		return new ResponseEntity<UsuarioDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.PUT, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Modifica usuario", response = MessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<MessageDTO> modifica(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody ModificaUsuarioDTO req)

			throws IonixException {

		MessageDTO response = service.modifica(req);
		return new ResponseEntity<MessageDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.OK);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Eliminar usuario", response = MessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<MessageDTO> elimina(@RequestHeader(value = "Authorization") String token,
			@Valid @PathVariable("id") Long id) throws IonixException {

		if (!validarToken(token)) {
			return new ResponseEntity<MessageDTO>(new MessageDTO(Constantes.M401, "401"),
					HeadersUtils.getGenericHeaders(), HttpStatus.OK);
		}

		MessageDTO response = service.elimina(id);
		return new ResponseEntity<MessageDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.OK);
	}

	@RequestMapping(path = "/registrar", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Registrar usuario", response = UsuarioDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<UsuarioDTO> registrar(@Valid @RequestBody CreaUsuarioDTO req) throws IonixException {

		UsuarioDTO response = service.registrar(req);
		return new ResponseEntity<UsuarioDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.CREATED);
	}

	@RequestMapping(path = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Login iusuario", response = UsuarioDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody LoginRequest req) throws IonixException {

		UsuarioDTO usuarioDTO = service.login(req);

		if (usuarioDTO.getId() == null) {
			return new ResponseEntity<UsuarioDTO>(new UsuarioDTO(), HeadersUtils.getGenericHeaders(),
					HttpStatus.FORBIDDEN);
		}

		return new ResponseEntity<UsuarioDTO>(usuarioDTO, HeadersUtils.getGenericHeaders(), HttpStatus.OK);
	}

	@RequestMapping(path = "/avatar", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ApiOperation(value = "Agregar usuario", response = MessageDTO.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constantes.M200),
			@ApiResponse(code = 400, message = Constantes.M400, response = MessageDTO.class),
			@ApiResponse(code = 401, message = Constantes.M401, response = MessageDTO.class),
			@ApiResponse(code = 403, message = Constantes.M403, response = MessageDTO.class),
			@ApiResponse(code = 404, message = Constantes.M404, response = MessageDTO.class),
			@ApiResponse(code = 500, message = Constantes.M500, response = MessageDTO.class),
			@ApiResponse(code = 502, message = Constantes.M502, response = MessageDTO.class),
			@ApiResponse(code = 503, message = Constantes.M503, response = MessageDTO.class),
			@ApiResponse(code = 504, message = Constantes.M504, response = MessageDTO.class) })
	public ResponseEntity<MessageDTO> avatar(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id)
			throws IonixException {

		try {
			MessageDTO response = service.guardarAvatar(id, file);
			return new ResponseEntity<MessageDTO>(response, HeadersUtils.getGenericHeaders(), HttpStatus.OK);
		} catch (IonixException e) {
			return new ResponseEntity<MessageDTO>(Utils.generarAvatarErrorMessageDTO(),
					HeadersUtils.getGenericHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private boolean validarToken(String token) {
		String usuarioId = null;
		try {
			usuarioId = jwtProvider.getKey(token);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return usuarioId != null;
	}

}
