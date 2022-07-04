package cl.ionix.ms.prueba.dtos;

import lombok.Data;

@Data
public class UsuarioDTO {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String token;
	private String avatar;

}
