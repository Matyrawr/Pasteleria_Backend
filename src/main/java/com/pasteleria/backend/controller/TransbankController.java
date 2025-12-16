package com.pasteleria.backend.controller;

import com.pasteleria.backend.dto.transbank.CreateTransactionResponse;
import com.pasteleria.backend.dto.transbank.CommitResponse;
import com.pasteleria.backend.dto.transbank.StatusResponse;
import com.pasteleria.backend.service.TransbankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;

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
    @Operation(summary = "Crear transacción Webpay con monto del carrito")
    public ResponseEntity<CreateTransactionResponse> createTransaction(@RequestBody CreateRequest request) {
        try {
            double amount = request != null ? request.amount : 0;
            if (amount <= 0) {
                return ResponseEntity.badRequest().build();
            }
            CreateTransactionResponse response = transbankService.createTransaction(amount);
            System.out.println("[Transbank] create -> url=" + response.getUrl() + ", token=" + response.getToken());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/commit")
    @Operation(summary = "Confirmar transacción (callback de returnUrl)")
    public ResponseEntity<CommitResponse> commit(@RequestParam("token_ws") String token) {
        try {
            System.out.println("[Transbank] commit token_ws=" + token);
            CommitResponse resp = transbankService.commitTransaction(token);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Soporte para pruebas manuales vía GET
    @org.springframework.web.bind.annotation.GetMapping("/commit")
    public ResponseEntity<CommitResponse> commitGet(@RequestParam("token_ws") String token) {
        try {
            System.out.println("[Transbank] commit(GET) token_ws=" + token);
            CommitResponse resp = transbankService.commitTransaction(token);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/status")
    @Operation(summary = "Consultar estado de transacción")
    public ResponseEntity<StatusResponse> status(@RequestParam("token_ws") String token) {
        try {
            System.out.println("[Transbank] status token_ws=" + token);
            StatusResponse resp = transbankService.getTransactionStatus(token);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    // No longer needed: returning typed DTOs
}

class CreateRequest {
    public double amount;
}
