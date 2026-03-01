package proyecto.proj.modelo;

import jakarta.persistence.*;


/**
 * Modelo de datos para un juego.
 * Se marca como una entidad y se le asigna una tabla "juegos" en la base de datos.
 */
@Entity
@Table(name = "juegos")
public class Juego {

    /** Identificador unico del juego, este es generado de manera automatica */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre del videojuego */
    @Column(nullable = false)
    private String nombre;

    /** Plataformas de los juegos ya sea PC, PS5, Xbox, Switch, etc. */
    @Column(nullable = false)
    private String plataforma;

    /** Precio del juego */
    @Column(nullable = false)
    private Double precio;

    /**
     * Constructor vacio, que es necesario para JPA.
     */
    public Juego() {
    }

    /**
     * Constructor con parametros para creacion de objetos de juego.
     * @param nombre nombre del juego
     * @param plataforma plataforma del juego
     * @param precio precio del juego
     */
    public Juego(String nombre, String plataforma, Double precio) {
        this.nombre = nombre;
        this.plataforma = plataforma;
        this.precio = precio;
    }


    //getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
