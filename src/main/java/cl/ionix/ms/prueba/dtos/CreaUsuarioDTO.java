package cl.ionix.ms.prueba.dtos;

import lombok.Data;

@Data
public class CreaUsuarioDTO {

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;

}
