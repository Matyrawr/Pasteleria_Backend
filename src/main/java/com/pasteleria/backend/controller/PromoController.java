package com.pasteleria.backend.controller;

import com.pasteleria.backend.dto.promo.PromoValidationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/promos")
@Tag(name = "Promos", description = "Validación de códigos promocionales")
public class PromoController {

    private final com.pasteleria.backend.repository.UserRepository userRepository;
    private final com.pasteleria.backend.repository.PromoUsageRepository promoUsageRepository;

    public PromoController(
            com.pasteleria.backend.repository.UserRepository userRepository,
            com.pasteleria.backend.repository.PromoUsageRepository promoUsageRepository) {
        this.userRepository = userRepository;
        this.promoUsageRepository = promoUsageRepository;
    }

    @GetMapping("/validate")
    @Operation(summary = "Validar código promocional por usuario y obtener porcentaje")
    public ResponseEntity<PromoValidationResponse> validate(@RequestParam(name = "code", required = false) String code) {
        if (code == null || code.trim().isEmpty()) {
            return ResponseEntity.ok(new PromoValidationResponse(null, false, 0));
        }

        String normalized = code.trim().toUpperCase();

        // Obtener usuario autenticado
        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        // Revisar si ya lo usó
        boolean alreadyUsed = promoUsageRepository.existsByUserAndCodeIgnoreCase(user, normalized);
        if (alreadyUsed) {
            return ResponseEntity.ok(new PromoValidationResponse(normalized, false, 0));
        }

        // Elegibilidad (reglas actuales)
        int percent = normalized.equals("FELICES50") ? 10 : 5;
        return ResponseEntity.ok(new PromoValidationResponse(normalized, true, percent));
    }

    @org.springframework.web.bind.annotation.PostMapping("/mark-used")
    @Operation(summary = "Marcar código como usado por el usuario actual")
    public ResponseEntity<Void> markUsed(@RequestParam(name = "code") String code) {
        if (code == null || code.trim().isEmpty()) return ResponseEntity.badRequest().build();
        String normalized = code.trim().toUpperCase();

        org.springframework.security.core.Authentication auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }
        String email = auth.getName();
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(401).build();
        }

        boolean alreadyUsed = promoUsageRepository.existsByUserAndCodeIgnoreCase(user, normalized);
        if (!alreadyUsed) {
            promoUsageRepository.save(new com.pasteleria.backend.model.PromoUsage(user, normalized));
        }
        return ResponseEntity.noContent().build();
    }
}
