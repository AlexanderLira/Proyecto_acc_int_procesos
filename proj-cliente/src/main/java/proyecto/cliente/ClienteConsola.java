package proyecto.cliente;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Scanner;

/**
 * Parte de cliente para gestion de ventas de juegos
 * Se conecta al servidor mediante peticiones HTTP
 */
public class ClienteConsola
{

    /** url base para el servidor */
    private static final String Base_url = "http://localhost:12345/api";
    /** Se usa scanner para leer la entrada de usuarios */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Metodo principal que muestra el menu y gestiona las opciones del usuario.
     * 
     */
    public static void main(String[] args)
    {
        int opcion;
        // Este do-while muestra el menu principal  permite elegir entre la gestion de juegos o ventas, o alir del programa.

        // A diferencia de do-whiles convencionales (o al menos el que uso en la mayoria de ocasiones) inicia con do en lugar de while
        // Esto ya que el menu deberia mostrarse al menos una vez, repitiendose hasta que se elija 0,
        // Si se usara el while directamente, existe la posbilidad de que el menu no se muestre ya que la variable opcion no se inicializa, causando un error
        // Esto funciona igual para los demas menus.
        do {
            System.out.println("\nGestion de ventas de juegos");
            System.out.println("1. Gestionar Juegos");
            System.out.println("2. Gestionar Ventas");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");
            opcion = leer_Entero();
            switch (opcion) {
                case 1:
                    menu_de_juegos();
                    break;
                case 2:
                    menu_de_ventas();
                    break;
                case 0:
                    System.out.println("Fin del programa");
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opcion != 0);
    }

    /** Muestra el submenu para gestionar juegos */
    private static void menu_de_juegos()
    {
        int opcion;
        do 
        {
            System.out.println("\nAcciones disponibles");
            System.out.println("1. Listar todos");
            System.out.println("2. Crear juego");
            System.out.println("3. Actualizar juego");
            System.out.println("4. Eliminar juego");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leer_Entero();
            switch (opcion) {
                case 1:
                    // Se llama al metodo hacer_get y se le pasa la ruta de juegos
                    hacer_Get("/juegos");
                    break;
                case 2:
                    crear_juego();
                    break;
                case 3:
                    actualizar_juego();
                    break;
                case 4:
                    eliminar_juego();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } 
        while (opcion != 0);
    }

    /** Muestra el submenu para gestionar ventas */
    private static void menu_de_ventas()
    {
        int opcion;
        do {
            System.out.println("\nAcciones disponibles");
            System.out.println("1. Listar todas");
            System.out.println("2. Crear venta");
            System.out.println("3. Actualizar venta");
            System.out.println("4. Eliminar venta");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            opcion = leer_Entero();
            switch (opcion) 
            {
                case 1:
                    // Se llama al metodo hacer_Get y se le pasa la ruta de ventas
                    hacer_Get("/ventas");
                    break;
                case 2:
                    crear_venta();
                    break;
                case 3:
                    actualizar_venta();
                    break;
                case 4:
                    eliminar_venta();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        } while (opcion != 0);
    }

    /** Pide los datos al usuario y crea un juego nuevo */
    private static void crear_juego()
    {
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Plataforma (PC, PS5, Xbox, Switch...): ");
        String plataforma = scanner.nextLine();
        System.out.print("Precio: ");
        double precio = leer_Double();
        String json = "{\"nombre\":\"" + nombre + "\", \"plataforma\":\"" + plataforma + "\", \"precio\":" + precio + "}";

        hacer_Post("/juegos", json);
    }

    /** Pide los datos al usuario y actualiza un juego existente */
    private static void actualizar_juego()
    {
        System.out.print("ID del juego a actualizar: ");
        int id = leer_Entero();
        System.out.print("Nuevo nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Nueva plataforma: ");
        String plataforma = scanner.nextLine();
        System.out.print("Nuevo precio: ");
        double precio = leer_Double();
        String json = "{\"nombre\":\"" + nombre + "\", \"plataforma\":\"" + plataforma + "\", \"precio\":" + precio + "}";

        hacer_Put("/juegos/" + id, json);
    }

    /** Pide el ID y elimina un juego */
    private static void eliminar_juego()
    {
        System.out.print("ID del juego a eliminar: ");
        int id = leer_Entero();

        hacer_Delete("/juegos/" + id);
    }

    /** Pide los datos al usuario y crea una venta nueva */
    private static void crear_venta()
    {
        System.out.print("Cantidad: ");
        int cantidad = leer_Entero();
        System.out.print("Fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("ID del juego: ");
        int juegoId = leer_Entero();
        String json = "{\"cantidad\":" + cantidad + ", \"fecha\":\"" + fecha + "\", \"juego\":{\"id\":" + juegoId + "}}";
        
        hacer_Post("/ventas", json);
    }

    /** Pide los datos al usuario y actualiza una venta existente */
    private static void actualizar_venta()
    {
        System.out.print("ID de la venta a actualizar: ");
        int id = leer_Entero();
        System.out.print("Nueva cantidad: ");
        int cantidad = leer_Entero();
        System.out.print("Nueva fecha (YYYY-MM-DD): ");
        String fecha = scanner.nextLine();
        System.out.print("ID del juego: ");
        int juegoId = leer_Entero();
        String json = "{\"cantidad\":" + cantidad + ", \"fecha\":\"" + fecha + "\", \"juego\":{\"id\":" + juegoId + "}}";
        
        hacer_Put("/ventas/" + id, json);
    }

    /** Pide el ID y elimina una venta */
    private static void eliminar_venta()
    {
        System.out.print("ID de la venta a eliminar: ");
        int id = leer_Entero();
        
        hacer_Delete("/ventas/" + id);
    }

    // Metodos disponibles para peticiones HTTP

    // Varios de estos metodos siguen una estructura similar, se abre la conexion (que es un metodo aparte) con el metodo y se les pasa la ruta y el metodo a realizar
    // Despues se envio el json en caso de ser necesario (en algunos metodos)

    //Despues se imprime la respuesta, este siendo el mensaje de respuesta con el codigo o una respuesta de error
    // Imprimir respuesta es un metodo aparte ya que se repite en los demas metodos HTTP

    /**
     * Realiza una peticion GET al servidor.
     * @param ruta ruta del endpoint (ejemplo: "/juegos")
     */
    private static void hacer_Get(String ruta) {
        try {
            HttpURLConnection con = abrir_Conexion(ruta, "GET");
            imprimir_Respuesta(con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Realiza la peticion Post al servidor a la vez que le envia datos en formato JSON.
     * @param ruta ruta del endpoint
     * @param json datos en formato JSON a enviar
     */
    private static void hacer_Post(String ruta, String json) {
        try {
            HttpURLConnection con = abrir_Conexion(ruta, "POST");
            enviar_Json(con, json);
            imprimir_Respuesta(con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Realiza la peticion Put al servidor a la vez que le envia datos en formato JSON
     * @param ruta ruta del endpoint
     * @param json datos en formato JSON a enviar
     */
    private static void hacer_Put(String ruta, String json) {
        try {
            HttpURLConnection con = abrir_Conexion(ruta, "PUT");
            enviar_Json(con, json);
            imprimir_Respuesta(con);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Realiza una peticion DELETE al servidor.
     * @param ruta ruta del endpoint con el ID del recurso a eliminar
     */
    private static void hacer_Delete(String ruta) {
        try 
        {

            HttpURLConnection con = abrir_Conexion(ruta, "DELETE");

            int codigo = con.getResponseCode();
            System.out.println("Codigo respuesta: " + codigo);
            if (codigo == 200) {
                System.out.println("Eliminado correctamente");
            } else {
                System.out.println("No encontrado");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Abre una conexion HTTP al servidor.
     * @param ruta ruta del endpoint
     * @param metodo metodo HTTP (GET, POST, PUT, DELETE)
     * @return la conexion abierta
     * @throws Exception si ocurre un error al conectar
     */
    private static HttpURLConnection abrir_Conexion(String ruta, String metodo) throws Exception {
        URI uri = URI.create(Base_url + ruta);
        HttpURLConnection con = (HttpURLConnection) uri.toURL().openConnection();
        con.setRequestMethod(metodo);
        con.setRequestProperty("Content-Type", "application/json");
        return con;
    }

    /**
     * Envia los datos a los metodos que lo necesiten en formato JSON.
     * @param con conexion HTTP abierta
     * @param json datos en formato JSON
     * @throws Exception si ocurre un error al enviar
     */
    private static void enviar_Json(HttpURLConnection con, String json) throws Exception {
        con.setDoOutput(true);
        try (OutputStream os = con.getOutputStream()) {
            os.write(json.getBytes());
        }
    }

    /**
     * Se lee y muestra la informacion de respuesta del servidor 
     * @param con conexion HTTP con la respuesta
     * @throws Exception si ocurre un error al leer
     */
    private static void imprimir_Respuesta(HttpURLConnection con) throws Exception
    {
        // Se imprime el codigo de respuesta, siendo positivo si es 200, y negativo para codigos de error 400(o superiores de error)
        int codigo = con.getResponseCode();
        System.out.println("Codigo respuesta: " + codigo);
        InputStream is;
        if (codigo >= 400) 
        {
            is = con.getErrorStream();
        } else 
        {
            is = con.getInputStream();
        }
        if (is != null) 
        {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    System.out.println(linea);
                }
            }
        }
    }

    // Se usan estos metodos para entradas numericas para enteros y doubles, esto para evitar errorres de formato que pasarian si se usara el scanner de manera directa

    /**
     * Lee un numero entero del teclado con validacion.
     * @return el numero entero introducido por el usuario
     */
    private static int leer_Entero()
    {
        while (true) {
            try {
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero valido: ");
            }
        }
    }

    /**
     * Lee un numero decimal del teclado con validacion.
     * @return el numero decimal introducido por el usuario
     */
    private static double leer_Double()
    {
        while (true) {
            try {
                double valor = Double.parseDouble(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.print("Introduce un numero valido: ");
            }
        }
    }
}
