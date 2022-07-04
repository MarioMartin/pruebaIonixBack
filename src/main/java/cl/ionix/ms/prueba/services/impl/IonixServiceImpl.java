package cl.ionix.ms.prueba.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cl.ionix.ms.prueba.dtos.CreaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.ListUsuariosResponse;
import cl.ionix.ms.prueba.dtos.LoginRequest;
import cl.ionix.ms.prueba.dtos.MessageDTO;
import cl.ionix.ms.prueba.dtos.ModificaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.UsuarioDTO;
import cl.ionix.ms.prueba.entities.UsuarioEntity;
import cl.ionix.ms.prueba.enums.InternalCodes;
import cl.ionix.ms.prueba.exceptions.IonixException;
import cl.ionix.ms.prueba.repositories.IonixRepository;
import cl.ionix.ms.prueba.security.JwtProvider;
import cl.ionix.ms.prueba.services.IonixService;
import cl.ionix.ms.prueba.utils.Constantes;
import cl.ionix.ms.prueba.utils.Utils;

@Service
public class IonixServiceImpl implements IonixService {

	private IonixRepository repo;
	private final Path rootFolder = Paths.get("uploads");

	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	public IonixServiceImpl(final IonixRepository repo) {
		this.modelMapper = new ModelMapper();
		this.passwordEncoder = new BCryptPasswordEncoder();
		this.repo = repo;
	}

	@Override
	public ListUsuariosResponse listar() {

		ListUsuariosResponse response = new ListUsuariosResponse();
		List<UsuarioEntity> listaEntity = (List<UsuarioEntity>) repo.findAll();
		List<UsuarioDTO> lista = listaEntity.stream().map(this::toListaDto).collect(Collectors.toList());
		response.setListaUsuarios(lista);

		return response;
	}

	@Override
	public UsuarioDTO añade(CreaUsuarioDTO req) {
		UsuarioEntity usuarioEntity = modelMapper.map(req, UsuarioEntity.class);

		usuarioEntity.setPassword(this.passwordEncoder.encode(usuarioEntity.getPassword()));

		UsuarioEntity usuarioGuardadoEntity = repo.save(usuarioEntity);

		UsuarioDTO usuarioGuardadoDTO = modelMapper.map(usuarioGuardadoEntity, UsuarioDTO.class);
		return usuarioGuardadoDTO;
	}

	@Override
	public UsuarioDTO obtieneUsuario(Long id) throws IonixException {
		Optional<UsuarioEntity> opEntity = repo.findById(id);

		if (opEntity.isPresent()) {

			return modelMapper.map(opEntity.get(), UsuarioDTO.class);

		} else {
			final String message = String.format(Constantes.M404);
			throw new IonixException(message, InternalCodes.USER_NOT_FOUND.getHttpStatusCode());
		}

	}

	@Override
	public MessageDTO modifica(ModificaUsuarioDTO req) {
		UsuarioEntity usuarioEntity = modelMapper.map(req, UsuarioEntity.class);
		repo.save(usuarioEntity);
		return Utils.generarOkMessageDTO();
	}

	@Override
	public MessageDTO elimina(Long id) {
		repo.deleteById(id);
		return Utils.generarOkMessageDTO();
	}

	private UsuarioDTO toListaDto(UsuarioEntity entity) {
		return modelMapper.map(entity, UsuarioDTO.class);
	}

	@Override
	public UsuarioDTO registrar(@Valid CreaUsuarioDTO req) throws IonixException {

		UsuarioEntity usuarioEntity = modelMapper.map(req, UsuarioEntity.class);
		usuarioEntity.setPassword(this.passwordEncoder.encode(usuarioEntity.getPassword()));

		UsuarioEntity userRegistrado = repo.save(usuarioEntity);
		UsuarioDTO usuarioDTO = modelMapper.map(userRegistrado, UsuarioDTO.class);

		String tokenJwt = jwtProvider.create(String.valueOf(userRegistrado.getId()), userRegistrado.getEmail());
		usuarioDTO.setToken(tokenJwt);

		return usuarioDTO;

	}

	@Override
	public UsuarioDTO login(LoginRequest req) throws IonixException

	{

		Optional<UsuarioEntity> opUsuario = repo.findByUsername(req.getUsername());

		if (opUsuario.isPresent()) {
			UsuarioEntity usuarioEnty = opUsuario.get();

			if (this.passwordEncoder.matches(req.getPassword(), usuarioEnty.getPassword())) {

				UsuarioDTO usuarioDTO = modelMapper.map(usuarioEnty, UsuarioDTO.class);

				String tokenJwt = jwtProvider.create(String.valueOf(usuarioEnty.getId()), usuarioEnty.getEmail());
				usuarioDTO.setToken(tokenJwt);
				return usuarioDTO;
			} else {
				throw new IonixException(Constantes.M401, 401);
			}
		} else {
			throw new IonixException(Constantes.M404, 404);
		}

	}

	@Override
	public MessageDTO guardarAvatar(Long id, MultipartFile file) throws IonixException {

		Optional<UsuarioEntity> opUsuario = repo.findById(id);

		if (opUsuario.isPresent()) {
			UsuarioEntity usuarioEntity = opUsuario.get();

			try {
				Files.copy(file.getInputStream(), this.rootFolder.resolve(file.getOriginalFilename()));
				String ruta = file.getOriginalFilename();
				usuarioEntity.setAvatar(ruta);
				repo.save(usuarioEntity);
			} catch (IOException e) {
				return Utils.generarAvatarErrorMessageDTO();
			}
		}

		return Utils.generarOkMessageDTO();
	}

}
