# Proyecto de Acceso a Datos, Interfaces y Servicios y Procesos

Osman Alexander Lira Caceres

Proyecto REST cliente-servidor para la gestion de ventas de videojuegos. Consiste en dos aplicaciones Java independientes: un servidor que publica servicios REST y un cliente de consola que los consume.


## Estructura del proyecto

El proyecto fue inicializado con Spring Boot, inicialmente el apartado de servidor y cliente estaban mas unificados, pero se han separado en dos proyectos diferentes:

- proj: Contiene el servidor REST.
- proj-cliente: Contiene el cliente de consola.

Ambos funcionan como dos proyectos por separado, aunque de los dos, el que tiene mas contenido es servidor.


## Clases usadas

Las clases principales para la gestion de ventas de juegos son Juego y Venta. Para cada una de estas se tiene un controlador, un modelo y un repositorio para que el cliente pueda realizar las diferentes funciones que necesite.

La relacion que se sigue es ManyToOne, siendo que un juego puede tener muchas ventas y una venta pertenece a un solo juego. Esta relacion es definida en el modelo de venta con la anotacion JPA de @ManyToOne junto a @JoinColumn, que define el nombre de la columna de clave foranea en la tabla ventas.


## Como funciona el servidor

Es la API REST construida con Spring Boot que expone los endpoints HTTP en el puerto 12345. Se cuentan con dos controladores:

- JuegoController (/api/juegos): CRUD de videojuegos, usa unicamente el repositorio JuegoRepository para la gestion de tablas en MySQL.
- VentaController (/api/ventas): CRUD para las ventas. A diferencia de JuegoController, este usa dos repositorios, el de VentaRepository y JuegoRepository, esto por la relacion ManyToOne de venta a juego. Al crear o actualizar una venta se verifica que el juego referenciado exista antes de guardar el cambio.


## Estos son los endpoints que estan disponibles h
Endpoints disponibles:

- GET /api/juegos - Listar todos los juegos
- GET /api/juegos/{id} - Obtener juego por ID
- POST /api/juegos - Crear un nuevo juego
- PUT /api/juegos/{id} - Actualizar un juego
- DELETE /api/juegos/{id} - Eliminar un juego
- GET /api/ventas - Listar todas las ventas
- GET /api/ventas/{id} - Obtener venta por ID
- POST /api/ventas - Crear una nueva venta
- PUT /api/ventas/{id} - Actualizar una venta
- DELETE /api/ventas/{id} - Eliminar una venta


## Como funciona el cliente

Cliente es una aplicacion Java independiente, no es de Spring como servidor. Se conecta al servidor en la direccion http://localhost:12345/api haciendo uso de HttpURLConnection de la libreria estandar de Java.

Muestra dos menus distintos, para la gestion de juegos y para la gestion de ventas de juegos. Los metodos HTTP siguen la misma infraestructura: abrir_conexion configura la conexion, enviar_Json escribe el cuerpo (para POST y PUT) e imprimir_respuesta lee la respuesta.


## Configuracion de la base de datos

El servidor usa MySQL. Es necesario tener MySQL en ejecucion y crear la base de datos "proyecto" antes de iniciar el servidor. La configuracion se encuentra en proj/src/main/resources/application.properties:

```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/proyecto
spring.datasource.username=Lira
spring.datasource.password=2004
```


## Como iniciar el programa

Iniciar el programa no es complicado. Primero se inicia el servidor:

```
cd proj
./mvnw spring-boot:run
```

El servidor arranca en el puerto 12345. Despues se inicia el cliente:

```
cd proj-cliente
./mvnw compile exec:java -Dexec.mainClass="proyecto.cliente.ClienteConsola"
```


## Pruebas unitarias

En servidor se hacen pruebas haciendo uso de JUnit combinado al uso de MockMvc para simular peticiones HTTP. Lo que se prueba son las peticiones HTTP que se hacen en juego y venta. Dicha simulacion no requiere que el servidor se inicie por completo.

Las pruebas usan una base de datos temporal H2, siendo que durante las pruebas los datos son creados en una base de datos llamada testdb y se borran cuando se acaba el proceso.

Para ejecutar las pruebas:

```
cd proj
./mvnw test
```


## Documentacion

Para la documentacion se hace uso de Javadoc. Los comentarios que presentan /** ..*/ son instrucciones para la herramienta de Javadoc que genera paginas HTML. Al hacer uso de @param, @return y @throws se generan secciones separadas en la documentacion.

Tambien se tiene documentacion de Swagger mediante las anotaciones @Operation junto al summary en los controladores, esto genera documentacion interactiva de la API que puede verse en http://localhost:12345/swagger-ui.html cuando el servidor esta en ejecucion.

Para generar la documentacion Javadoc:

```
cd proj
./mvnw javadoc:javadoc

cd proj-cliente
./mvnw javadoc:javadoc
```

La documentacion se genera en target/site/apidocs/index.html de cada proyecto.


## Para la generacion de los ficheros JAR

```
cd proj
./mvnw package -DskipTests

cd proj-cliente
./mvnw package
```

Para desplegar el servidor: java -jar proj/target/proj-0.0.1-SNAPSHOT.jar

Para desplegar el cliente: java -jar proj-cliente/target/proj-cliente-0.0.1-SNAPSHOT.jar


## Autor

Osman Alexander Lira Caceres
