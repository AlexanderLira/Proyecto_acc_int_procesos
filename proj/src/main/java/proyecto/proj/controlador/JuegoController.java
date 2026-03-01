package proyecto.proj.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.proj.modelo.Juego;
import proyecto.proj.repositorio.JuegoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestion de juegos.
 * Proporciona los endpoints CRUD para crear, leer, actualizar y eliminar juegos.
 */
@RestController
@RequestMapping("/api/juegos")
@Tag(name = "Juegos", description = "Metodo crud para gestion de los videojuegos")
public class JuegoController {

    private final JuegoRepository repositorio;

    /**
     * Constructor que recibe el repositorio de juegos.
     * @param repositorio repositorio JPA para acceder a los juegos
     */
    public JuegoController(JuegoRepository repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * Lista todos los juegos de la base de datos.
     * @return lista de juegos
     */
    @GetMapping
    @Operation(summary = "Listar todos los juegos")
    public List<Juego> listar() {
        return repositorio.findAll();
    }

    /**
     * Obtiene un juego por su ID.
     * @param id identificador del juego
     * @return el juego encontrado o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener un juego por ID")
    public ResponseEntity<Juego> obtener(@PathVariable Long id) 
    {
        Optional<Juego> resultado = repositorio.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea un nuevo juego en la base de datos.
     * @param juego datos del juego a crear
     * @return el juego creado
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo juego")
    public Juego crear(@RequestBody Juego juego) {
        return repositorio.save(juego);
    }

    /**
     * Actualiza un juego existente.
     * @param id identificador del juego a actualizar
     * @param juego datos nuevos del juego
     * @return el juego actualizado o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un juego existente")
    public ResponseEntity<Juego> actualizar(@PathVariable Long id, @RequestBody Juego juego) {
        Optional<Juego> resultado = repositorio.findById(id);
        if (resultado.isPresent()) {
            Juego existente = resultado.get();
            existente.setNombre(juego.getNombre());
            existente.setPlataforma(juego.getPlataforma());
            existente.setPrecio(juego.getPrecio());
            return ResponseEntity.ok(repositorio.save(existente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina un juego por su ID.
     * @param id identificador del juego a eliminar
     * @return 200 si se elimino o 404 si no existe
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un juego")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (repositorio.existsById(id)) {
            repositorio.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
