package cl.ionix.ms.prueba.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ListUsuariosResponse {

	private List<UsuarioDTO> listaUsuarios;

}
