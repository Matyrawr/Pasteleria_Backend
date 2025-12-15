package com.pasteleria.backend.controller;

import com.pasteleria.backend.dto.ProductoRequest;
import com.pasteleria.backend.model.producto;
import com.pasteleria.backend.service.productoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API REST para gestionar productos de la pastelería")
public class productoController {

    private final productoService service;

    public productoController(productoService service) {
        this.service = service;
    }

    // ============================================================================
    // GET: LISTAR TODOS LOS PRODUCTOS
    // ============================================================================
    @GetMapping
    @Operation(
            summary = "Listar todos los productos",
            description = "Devuelve una lista completa de todos los productos registrados."
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente",
            content = @Content(mediaType = "application/json"))
    public ResponseEntity<List<producto>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    // ============================================================================
    // GET: OBTENER POR ID
    // ============================================================================
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener un producto por su ID",
            description = "Devuelve los datos de un producto específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<producto> obtenerPorId(
            @Parameter(description = "ID del producto a consultar", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    // ============================================================================
    // POST: CREAR NUEVO PRODUCTO
    // ============================================================================
    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto utilizando un DTO con validaciones."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = producto.class))),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<producto> crear(
            @Parameter(description = "Datos del nuevo producto (DTO)", required = true)
            @Valid @RequestBody ProductoRequest request) {

        producto nuevo = new producto();
        nuevo.setNombre(request.getNombre());
        nuevo.setDescripcion(request.getDescripcion());
        nuevo.setPrecio(request.getPrecio());
        nuevo.setStock(request.getStock());
        nuevo.setCategoria(request.getCategoria());

        producto creado = service.create(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ============================================================================
    // PUT: ACTUALIZAR PRODUCTO
    // ============================================================================
    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto existente",
            description = "Modifica los datos de un producto utilizando un DTO validado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<producto> actualizar(
            @Parameter(description = "ID del producto a actualizar", example = "1")
            @PathVariable Long id,

            @Parameter(description = "Nuevos datos del producto (DTO)", required = true)
            @Valid @RequestBody ProductoRequest request) {

        producto datos = new producto();
        datos.setNombre(request.getNombre());
        datos.setDescripcion(request.getDescripcion());
        datos.setPrecio(request.getPrecio());
        datos.setStock(request.getStock());
        datos.setCategoria(request.getCategoria());

        producto actualizado = service.update(id, datos);
        return ResponseEntity.ok(actualizado);
    }

    // ============================================================================
    // DELETE: ELIMINAR PRODUCTO
    // ============================================================================
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto a eliminar", example = "1")
            @PathVariable Long id) {

        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
