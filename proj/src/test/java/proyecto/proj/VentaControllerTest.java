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

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Esto es un metodo auxiliar para crear un juego de manera previa, ya que ventas necesitan tener un juego asociado

    private void crear_juego_previo() throws Exception {
        mockMvc.perform(post("/api/juegos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"GTA V\", \"plataforma\":\"PC\", \"precio\":29.99}"))
                .andExpect(status().isOk());
    }

    // Test para listar las ventas, se espera que este vacia al inicio.
    @Test
    void listar_ventas_vacio() throws Exception {
        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    // Test para crear una venta con el juego asociado 
    @Test
    void crear_venta() throws Exception {
        crear_juego_previo();

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":3, \"fecha\":\"2026-02-20\", \"juego\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(3))
                .andExpect(jsonPath("$.fecha").value("2026-02-20"));
    }

    // Test para crear una venta sin asociar un juego, debido a la relacion que tiene venta con juego...
    // Se espera que se reciba un error de Badrequest, ya que el campo de "juego" es obligatorio para ventas
    @Test
    void crear_venta_sin_juego() throws Exception {
        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":3, \"fecha\":\"2026-02-20\"}"))
                .andExpect(status().isBadRequest());
    }

    // Similar al anterior, en este caso se intenta asociar un juego que no existe.
    @Test
    void crear_venta_juego_no_existe() throws Exception {
        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":3, \"fecha\":\"2026-02-20\", \"juego\":{\"id\":999}}"))
                .andExpect(status().isBadRequest());
    }

    // Test para actualizar una venta existente, se crea la venta de manera temporal y despues se actualiza la misma venta.
    @Test
    void actualizar_venta() throws Exception {
        crear_juego_previo();

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":3, \"fecha\":\"2026-02-20\", \"juego\":{\"id\":1}}"))
                .andExpect(status().isOk());

        mockMvc.perform(put("/api/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":5, \"fecha\":\"2026-03-01\", \"juego\":{\"id\":1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(5))
                .andExpect(jsonPath("$.fecha").value("2026-03-01"));
    }

    // Test para eliminar una venta existente, se crea la venta de manera temporal y despues se elimina la misma venta.
    @Test
    void eliminar_venta() throws Exception {
        crear_juego_previo();

        mockMvc.perform(post("/api/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cantidad\":3, \"fecha\":\"2026-02-20\", \"juego\":{\"id\":1}}"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/api/ventas/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/ventas/1"))
                .andExpect(status().isNotFound());
    }
}
