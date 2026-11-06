
package com.clima.clima.controller;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.service.CiudadService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@CrossOrigin(origins = "http://localhost:5173")
public class CiudadController {

    private final CiudadService ciudadService;

    public CiudadController(CiudadService ciudadService) {
        this.ciudadService = ciudadService;
    }

    // Listar todas las ciudades
    @GetMapping
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        return ResponseEntity.ok(ciudadService.listarCiudades());
    }

    // Crear una ciudad
    @PostMapping
    public ResponseEntity<Ciudad> guardarCiudad(
            @Valid @RequestBody Ciudad ciudad) {

        Ciudad ciudadGuardada = ciudadService.guardarCiudad(ciudad);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ciudadGuardada);
    }

    // Buscar una ciudad por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> buscarPorId(@PathVariable Long id) {

        return ciudadService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar una ciudad
    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> actualizarCiudad(
            @PathVariable Long id,
            @Valid @RequestBody Ciudad datos) {

        return ciudadService.actualizarCiudad(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar una ciudad
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Long id) {

        boolean eliminada = ciudadService.eliminarCiudad(id);

        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
