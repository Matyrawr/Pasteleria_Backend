package com.pasteleria.backend.controller;

import com.pasteleria.backend.service.TransbankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/transbank")
@Tag(name = "Transbank", description = "API REST para gestionar pagos con Transbank")
public class TransbankController {

    private final TransbankService transbankService;

    public TransbankController(TransbankService transbankService) {
        this.transbankService = transbankService;
    }

    @PostMapping("/create")
    @Operation(summary = "Crear una transacción de prueba en Transbank")
    public ResponseEntity<String> createTransaction() {
        try {
            String response = transbankService.createTransaction();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
