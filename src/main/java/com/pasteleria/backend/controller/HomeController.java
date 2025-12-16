package com.pasteleria.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Void> rootRedirect() {
        // Redirige a la lista de productos en JSON
        return ResponseEntity.status(302).location(URI.create("/api/productos")).build();
    }
}
