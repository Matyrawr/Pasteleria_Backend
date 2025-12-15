package com.pasteleria.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pasteleria.backend.model.producto;
import com.pasteleria.backend.repository.productoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private productoRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        producto p = new producto();
        p.setNombre("Torta de chocolate");
        p.setDescripcion("Torta casera");
        p.setPrecio(new BigDecimal("12000"));
        p.setStock(5);
        p.setCategoria("TORTA");
        repository.save(p);
    }

    @Test
    void listarProductos_devuelve200YLista() throws Exception {
        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Torta de chocolate"));
    }

    @Test
    void crearProducto_devuelve201() throws Exception {
        producto nuevo = new producto();
        nuevo.setNombre("Pie de limón");
        nuevo.setDescripcion("Clásico");
        nuevo.setPrecio(new BigDecimal("8000"));
        nuevo.setStock(10);
        nuevo.setCategoria("POSTRE");

        String json = objectMapper.writeValueAsString(nuevo);

        mockMvc.perform(post("/api/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Pie de limón"));
    }
}
