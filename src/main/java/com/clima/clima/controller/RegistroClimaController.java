package com.clima.clima.controller;

import com.clima.clima.entity.RegistroClima;
import com.clima.clima.service.RegistroClimaService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mediciones")
@CrossOrigin (origins = "http://localhost:5173")
public class RegistroClimaController {

    private final RegistroClimaService RegistroClimaService;

    public RegistroClimaController(RegistroClimaService RegistroClimaService) {
        this.RegistroClimaService = RegistroClimaService;
    }

    // Listar todos los registros de clima
    @GetMapping
    public ResponseEntity<List<RegistroClima>> listarRegistroClimas() {
        return ResponseEntity.ok(RegistroClimaService.listarRegistroClima());
    }

    // Crear un registro de clima
    @PostMapping
    public ResponseEntity<RegistroClima> guardarRegistroClima
            (@Valid @RequestBody RegistroClima RegistroClima) {

        RegistroClima registroClimaGuardado = RegistroClimaService.guardarRegistroClima(RegistroClima);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registroClimaGuardado);
    }

    // Buscar un registro de clima por ID
    @GetMapping("/{id}")
    public ResponseEntity<RegistroClima> buscarPorId(@PathVariable Long id) {
        
        return RegistroClimaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualizar un registro de clima
    @PutMapping("/{id}")
    public ResponseEntity<RegistroClima> actualizarRegistroClima(
            @PathVariable Long id,
            @Valid @RequestBody RegistroClima datos) {

        return RegistroClimaService.actualizarRegistroClima(id, datos)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un registro de clima
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRegistroClima(@PathVariable Long id) {
        boolean eliminado = RegistroClimaService.eliminarRegistroClima(id);
        if (eliminado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}