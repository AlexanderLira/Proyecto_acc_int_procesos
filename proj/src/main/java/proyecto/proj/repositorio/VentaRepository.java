package proyecto.proj.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyecto.proj.modelo.Venta;

/**
 * Repositorio JPA para la entidad Venta.
 * Proporciona metodos CRUD heredados de JpaRepository.
 */
@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
