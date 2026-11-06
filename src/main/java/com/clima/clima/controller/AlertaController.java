package com.clima.clima.controller;

import com.clima.clima.entity.Alerta;
import com.clima.clima.service.AlertaService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alertas")
@CrossOrigin(origins = "http://localhost:5173")
public class AlertaController {

    private final AlertaService alertaService;

    public AlertaController(AlertaService alertaService) {
        this.alertaService = alertaService;
    }

    // GET /api/alertas
    @GetMapping
    public ResponseEntity<List<Alerta>> listarAlertas() {
        return ResponseEntity.ok(alertaService.listarAlertas());
    }

    // GET /api/alertas/nivel/{nivel}  -> BAJO, MEDIO o ALTO
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Alerta>> listarPorNivel(@PathVariable String nivel) {
        return ResponseEntity.ok(alertaService.listarPorNivel(nivel));
    }

    // GET /api/alertas/ciudad/{id}/activas  -> alertas activas de una ciudad
    @GetMapping("/ciudad/{id}/activas")
    public ResponseEntity<List<Alerta>> listarActivasPorCiudad(@PathVariable Long id) {
        return ResponseEntity.ok(alertaService.listarActivasPorCiudad(id));
    }

    // GET /api/alertas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> buscarPorId(@PathVariable Long id) {
        return alertaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/alertas
    @PostMapping
    public ResponseEntity<Alerta> guardarAlerta(@Valid @RequestBody Alerta alerta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alertaService.guardarAlerta(alerta));
    }

    // PUT /api/alertas/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Alerta> actualizarAlerta(@PathVariable Long id, @Valid @RequestBody Alerta alerta) {
        return alertaService.actualizarAlerta(id, alerta)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/alertas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlerta(@PathVariable Long id) {
        return alertaService.eliminarAlerta(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}