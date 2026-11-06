package com.clima.clima.controller;

import com.clima.clima.entity.RegistroClima;
import com.clima.clima.service.RegistroClimaService;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mediciones")
@CrossOrigin(origins = "http://localhost:5173")
public class RegistroClimaController {

    private final RegistroClimaService registroClimaService;

    public RegistroClimaController(RegistroClimaService registroClimaService) {
        this.registroClimaService = registroClimaService;
    }

    // GET /api/mediciones
    @GetMapping
    public ResponseEntity<List<RegistroClima>> listarRegistroClima() {
        return ResponseEntity.ok(registroClimaService.listarRegistroClima());
    }

    // GET /api/mediciones/estacion/{id}  -> mediciones de una estación
    @GetMapping("/estacion/{id}")
    public ResponseEntity<List<RegistroClima>> listarPorEstacion(@PathVariable Long id) {
        return ResponseEntity.ok(registroClimaService.listarPorEstacion(id));
    }

    // GET /api/mediciones/temperatura/mayor/30  -> mediciones con temperatura superior a un valor
    @GetMapping("/temperatura/mayor/{valor}")
    public ResponseEntity<List<RegistroClima>> listarConTemperaturaMayorA(@PathVariable BigDecimal valor) {
        return ResponseEntity.ok(registroClimaService.listarConTemperaturaMayorA(valor));
    }

    // GET /api/mediciones/fecha?desde=2026-11-01T00:00:00&hasta=2026-11-30T23:59:59
    @GetMapping("/fecha")
    public ResponseEntity<List<RegistroClima>> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(registroClimaService.listarPorFecha(desde, hasta));
    }

    // GET /api/mediciones/ciudad/{id}/promedio-temperatura  -> temperatura promedio de una ciudad
    @GetMapping("/ciudad/{id}/promedio-temperatura")
    public ResponseEntity<Map<String, Object>> obtenerTemperaturaPromedio(@PathVariable Long id) {
        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("ciudadId", id);
        respuesta.put("temperaturaPromedio", registroClimaService.obtenerTemperaturaPromedio(id));
        return ResponseEntity.ok(respuesta);
    }

    // GET /api/mediciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RegistroClima> buscarPorId(@PathVariable Long id) {
        return registroClimaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/mediciones
    @PostMapping
    public ResponseEntity<RegistroClima> guardarRegistroClima(@Valid @RequestBody RegistroClima registroClima) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registroClimaService.guardarRegistroClima(registroClima));
    }

    // PUT /api/mediciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<RegistroClima> actualizarRegistroClima(
            @PathVariable Long id, @Valid @RequestBody RegistroClima registroClima) {
        return registroClimaService.actualizarRegistroClima(id, registroClima)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/mediciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistroClima(@PathVariable Long id) {
        return registroClimaService.eliminarRegistroClima(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}