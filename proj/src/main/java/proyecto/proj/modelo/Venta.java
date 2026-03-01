package proyecto.proj.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Modelo de datos para venta.
 * Se marca como entidad y se le asigna "ventas" como tabla en la base de datos.
 */
@Entity
@Table(name = "ventas")
public class Venta {

    /** Identificador unico de la venta, generado automaticamente */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Cantidad de unidades vendidas */
    @Column(nullable = false)
    private Integer cantidad;

    /** Fecha en la que se realizo la venta */
    @Column(nullable = false)
    private LocalDate fecha;

    /** Juego asociado a esta venta (relacion muchos a uno) */
    @ManyToOne
    @JoinColumn(name = "juego_id", nullable = false)
    private Juego juego;

    /**
     * Constructor vacio, necesario para JPA.
     */
    public Venta() {
    }

    /**
     * Constructor con parametros para la creacion de objetos de venta.
     * @param cantidad cantidad de unidades vendidas
     * @param fecha fecha de la venta
     * @param juego juego asociado a la venta
     */
    public Venta(Integer cantidad, LocalDate fecha, Juego juego) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.juego = juego;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }
}
