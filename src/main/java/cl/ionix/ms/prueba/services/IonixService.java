package cl.ionix.ms.prueba.services;

import org.springframework.web.multipart.MultipartFile;

import cl.ionix.ms.prueba.dtos.CreaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.ListUsuariosResponse;
import cl.ionix.ms.prueba.dtos.LoginRequest;
import cl.ionix.ms.prueba.dtos.MessageDTO;
import cl.ionix.ms.prueba.dtos.ModificaUsuarioDTO;
import cl.ionix.ms.prueba.dtos.UsuarioDTO;
import cl.ionix.ms.prueba.exceptions.IonixException;

public interface IonixService {

	ListUsuariosResponse listar();

	UsuarioDTO añade(CreaUsuarioDTO req) throws IonixException;

	UsuarioDTO obtieneUsuario(Long id) throws IonixException;

	MessageDTO modifica(ModificaUsuarioDTO req) throws IonixException;

	MessageDTO elimina(Long id) throws IonixException;

	UsuarioDTO login(LoginRequest req) throws IonixException;

	MessageDTO guardarAvatar(Long id, MultipartFile file) throws IonixException;

	UsuarioDTO registrar(CreaUsuarioDTO req) throws IonixException;

}
