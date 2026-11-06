package com.clima.clima.controller;

import com.clima.clima.entity.Estacion;
import com.clima.clima.service.EstacionService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
@CrossOrigin(origins = "http://localhost:5173")
public class EstacionController {

    private final EstacionService estacionService;

    public EstacionController(EstacionService estacionService) {
        this.estacionService = estacionService;
    }

    // GET /api/estaciones
    @GetMapping
    public ResponseEntity<List<Estacion>> listarEstaciones() {
        return ResponseEntity.ok(estacionService.listarEstaciones());
    }

    // GET /api/estaciones/ciudad/{id}  -> estaciones de una ciudad
    @GetMapping("/ciudad/{id}")
    public ResponseEntity<List<Estacion>> listarPorCiudad(@PathVariable Long id) {
        return ResponseEntity.ok(estacionService.listarPorCiudad(id));
    }

    // GET /api/estaciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Estacion> buscarPorId(@PathVariable Long id) {
        return estacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/estaciones
    @PostMapping
    public ResponseEntity<Estacion> guardarEstacion(@Valid @RequestBody Estacion estacion) {
        return ResponseEntity.status(HttpStatus.CREATED).body(estacionService.guardarEstacion(estacion));
    }

    // PUT /api/estaciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Estacion> actualizarEstacion(@PathVariable Long id, @Valid @RequestBody Estacion estacion) {
        return estacionService.actualizarEstacion(id, estacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/estaciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstacion(@PathVariable Long id) {
        return estacionService.eliminarEstacion(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}