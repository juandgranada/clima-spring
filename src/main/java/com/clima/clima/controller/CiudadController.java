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

    // GET /api/ciudades
    @GetMapping
    public ResponseEntity<List<Ciudad>> listarCiudades() {
        return ResponseEntity.ok(ciudadService.listarCiudades());
    }

    // GET /api/ciudades/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> buscarPorId(@PathVariable Long id) {
        return ciudadService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/ciudades
    @PostMapping
    public ResponseEntity<Ciudad> guardarCiudad(@Valid @RequestBody Ciudad ciudad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.guardarCiudad(ciudad));
    }

    // PUT /api/ciudades/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> actualizarCiudad(@PathVariable Long id, @Valid @RequestBody Ciudad ciudad) {
        return ciudadService.actualizarCiudad(id, ciudad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/ciudades/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCiudad(@PathVariable Long id) {
        return ciudadService.eliminarCiudad(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}