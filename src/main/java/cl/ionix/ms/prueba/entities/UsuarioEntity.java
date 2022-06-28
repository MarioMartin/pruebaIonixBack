package cl.ionix.ms.prueba.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "USUARIO", schema = "INFRACCIONES_F2")
@Data
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_ID_GEN")
	@SequenceGenerator(name = "USUARIO_ID_GEN", sequenceName = "USUARIO_SEQ_CDG", initialValue = 1, allocationSize = 1)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "AVATAR")
	private String avatar;
}
