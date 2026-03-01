package proyecto.proj;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Pruebas unitarias para el controlador de juegos

@SpringBootTest
@AutoConfigureMockMvc

// Con el uso de DirtiesContext se asegura que cada prueba se ejecute en un lienzo en blanco, siendo que se generan los datos durante la prueba y despues estos se borran.
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class JuegoControllerTest {

        // Se usa MockMvc para simular las peticiones HTTP al controlador sin necesidad de iniciar el servidor completo
    @Autowired
    private MockMvc mockMvc;


    // Se hace un test de get para listar los juegos, esperando que este vacia.
    @Test
    void listar_juegos_vacio() throws Exception 
    {
        mockMvc.perform(get("/api/juegos"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
    // Se hace un test de post para crear el juego, se espera que se cree el juego correctamente.

    @Test
    void crear_juego() throws Exception {
        mockMvc.perform(post("/api/juegos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Zelda\", \"plataforma\":\"Switch\", \"precio\":59.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Zelda"))
                .andExpect(jsonPath("$.plataforma").value("Switch"))
                .andExpect(jsonPath("$.precio").value(59.99));
    }

    // Se hace un test de get para obtener un juego por id, aunque primero se crea de manera temporal el juego con un post
    // Y despues se busca ese mismo juego por id
    @Test
    void obtener_juego_por_id() throws Exception {
        mockMvc.perform(post("/api/juegos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Zelda\", \"plataforma\":\"Switch\", \"precio\":59.99}"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/juegos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Zelda"));
    }

    // Se hace un test para obtener un juego por id que no existe.
    @Test
    void obtener_juego_no_existe() throws Exception {
        mockMvc.perform(get("/api/juegos/999"))
                .andExpect(status().isNotFound());
    }

    // Se hace un test de put para actualizar un juego, al igual que antes, se crea primero el juego de manera temporal,
    // y despues se actualiza ese mismo juego por id, esperando que se actualice correctamente.
    @Test
    void actualizar_juego() throws Exception {
        mockMvc.perform(post("/api/juegos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Zelda\", \"plataforma\":\"Switch\", \"precio\":59.99}"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/juegos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Zelda TOTK\", \"plataforma\":\"Switch\", \"precio\":69.99}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Zelda TOTK"))
                .andExpect(jsonPath("$.precio").value(69.99));
    }

    // Se hace un test de put para actualizar un juego , se crea y despues se prueba a eliminarlo
    // Si el juego se elimina correctamente, no se deberia poder obtener
    // Si no se obtiene, se pasa la prueba, si se obtiene, se falla la prueba
    @Test
    void eliminar_juego() throws Exception {
        mockMvc.perform(post("/api/juegos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Zelda\", \"plataforma\":\"Switch\", \"precio\":59.99}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/juegos/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/juegos/1"))
                .andExpect(status().isNotFound());
    }

    // Se hace un test de delete para eliminar un juego que no existe.
    @Test
    void eliminar_juego_no_existe() throws Exception {
        mockMvc.perform(delete("/api/juegos/999"))
                .andExpect(status().isNotFound());
    }
}
