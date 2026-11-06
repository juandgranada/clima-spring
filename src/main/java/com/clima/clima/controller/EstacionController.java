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
@CrossOrigin (origins = "http://localhost:5173")
public class EstacionController {

    private final EstacionService EstacionService;

    public EstacionController(EstacionService estacionService) {
        this.EstacionService = estacionService;
    }

    // Listar todas las estaciones
    @GetMapping
    public ResponseEntity<List<Estacion>> listarEstacions() {
        return ResponseEntity.ok(EstacionService.listarEstaciones());
    }

    // Crear una estación
    @PostMapping
    public ResponseEntity<Estacion> guardarEstacion
            (@Valid @RequestBody Estacion estacion) {

        Estacion estacionGuardada = EstacionService.guardarEstacion(estacion);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estacionGuardada);
    }

    // Buscar una estación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estacion> buscarPorId(@PathVariable Long id) {
        
        return EstacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar una ciudad
    @PutMapping("/{id}")
    public ResponseEntity<Estacion> actualizarEstacion(
            @PathVariable Long id,
            @Valid @RequestBody Estacion datos) {

        return EstacionService.actualizarEstacion(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Eliminar una ciudad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstacion(@PathVariable Long id) {

        boolean eliminada = EstacionService.eliminarEstacion(id);

        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}