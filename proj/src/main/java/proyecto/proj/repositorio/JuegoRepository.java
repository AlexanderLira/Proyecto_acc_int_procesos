package proyecto.proj.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.proj.modelo.Juego;

/**
 * Repositorio JPA para la entidad Juego.
 * Proporciona metodos CRUD heredados de JpaRepository.
 */
@Repository
public interface JuegoRepository extends JpaRepository<Juego, Long> {
}
