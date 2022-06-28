package cl.ionix.ms.prueba.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import cl.ionix.ms.prueba.entities.UsuarioEntity;

public interface IonixRepository extends CrudRepository<UsuarioEntity, Long> {

	Optional<UsuarioEntity> findByEmail(String email);

}
