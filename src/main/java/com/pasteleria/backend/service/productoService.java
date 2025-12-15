package com.pasteleria.backend.service;

import com.pasteleria.backend.model.producto;
import com.pasteleria.backend.repository.productoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productoService {

    private final productoRepository repository;

    public productoService(productoRepository repository) {
        this.repository = repository;
    }

    public List<producto> findAll() {
        return repository.findAll();
    }

    public producto findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id " + id));
    }

    public producto create(producto p) {
        p.setId(null);
        return repository.save(p);
    }

    public producto update(Long id, producto datos) {
        producto existente = findById(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrecio(datos.getPrecio());
        existente.setStock(datos.getStock());
        existente.setCategoria(datos.getCategoria());
        return repository.save(existente);
    }

    public void delete(Long id) {
        producto existente = findById(id);
        repository.delete(existente);
    }
}
