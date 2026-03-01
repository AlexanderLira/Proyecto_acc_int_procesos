package proyecto.proj.controlador;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import proyecto.proj.modelo.Juego;
import proyecto.proj.modelo.Venta;
import proyecto.proj.repositorio.JuegoRepository;
import proyecto.proj.repositorio.VentaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para la gestion de ventas.
 * Proporciona los endpoints CRUD para crear, leer, actualizar y eliminar ventas.
 */
@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "CRUD de ventas de videojuegos")
public class VentaController {

    private final VentaRepository ventaRepositorio;
    private final JuegoRepository juegoRepositorio;

    /**
     * Constructor que recibe los repositorios necesarios.
     * @param ventaRepositorio repositorio JPA para acceder a las ventas
     * @param juegoRepositorio repositorio JPA para acceder a los juegos
     */
    public VentaController(VentaRepository ventaRepositorio, JuegoRepository juegoRepositorio) {
        this.ventaRepositorio = ventaRepositorio;
        this.juegoRepositorio = juegoRepositorio;
    }

    /**
     * Lista todas las ventas de la base de datos.
     * @return lista de ventas
     */
    @GetMapping
    @Operation(summary = "Listar todas las ventas")
    public List<Venta> listar() {
        return ventaRepositorio.findAll();
    }

    /**
     * Obtiene una venta por su ID.
     * @param id identificador de la venta
     * @return la venta encontrada o 404 si no existe
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una venta por ID")
    public ResponseEntity<Venta> obtener(@PathVariable Long id) {
        Optional<Venta> resultado = ventaRepositorio.findById(id);
        if (resultado.isPresent()) {
            return ResponseEntity.ok(resultado.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Crea una nueva venta. Se debe indicar el ID del juego asociado.
     * @param venta datos de la venta a crear
     * @return la venta creada o error si el juego no existe
     */
    @PostMapping
    @Operation(summary = "Crear una nueva venta")
    public ResponseEntity<?> crear(@RequestBody Venta venta) {
        if (venta.getJuego() == null || venta.getJuego().getId() == null) {
            return ResponseEntity.badRequest().body("Se debe indicar el ID del juego");
        }
        if (!juegoRepositorio.existsById(venta.getJuego().getId())) {
            return ResponseEntity.badRequest().body("El juego indicado no existe");
        }
        // Sin esta linea, se recibiria un errorm referencial, recibiendo solo el id, sin los demas campos del juego, por lo que se busca el juego completo por id y se asigna a la venta antes de guardarla
        venta.setJuego(juegoRepositorio.findById(venta.getJuego().getId()).get());
        return ResponseEntity.ok(ventaRepositorio.save(venta));
    }

    /**
     * Actualiza una venta existente.
     * @param id identificador de la venta a actualizar
     * @param venta datos nuevos de la venta
     * @return la venta actualizada o 404 si no existe
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una venta existente")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta venta) {
        Optional<Venta> resultado = ventaRepositorio.findById(id);
        if (resultado.isPresent()) {
            Venta existente = resultado.get();
            existente.setCantidad(venta.getCantidad());
            existente.setFecha(venta.getFecha());
            if (venta.getJuego() != null && venta.getJuego().getId() != null) {
                Optional<Juego> juegoOpt = juegoRepositorio.findById(venta.getJuego().getId());
                if (juegoOpt.isPresent()) {
                    existente.setJuego(juegoOpt.get());
                }
            }
            return ResponseEntity.ok(ventaRepositorio.save(existente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una venta por su ID.
     * @param id identificador de la venta a eliminar
     * @return 200 si se elimino o 404 si no existe
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una venta")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaRepositorio.existsById(id)) {
            ventaRepositorio.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
