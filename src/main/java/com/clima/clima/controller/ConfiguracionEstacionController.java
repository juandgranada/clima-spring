package com.clima.clima.controller;

import com.clima.clima.entity.ConfiguracionEstacion;
import com.clima.clima.service.ConfiguracionEstacionService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuraciones")
@CrossOrigin(origins = "http://localhost:5173")
public class ConfiguracionEstacionController {

    private final ConfiguracionEstacionService configuracionService;

    public ConfiguracionEstacionController(ConfiguracionEstacionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    // GET /api/configuraciones
    @GetMapping
    public ResponseEntity<List<ConfiguracionEstacion>> listarConfiguraciones() {
        return ResponseEntity.ok(configuracionService.listarConfiguraciones());
    }

    // GET /api/configuraciones/estacion/{id}  -> configuración de una estación (relación 1:1)
    @GetMapping("/estacion/{id}")
    public ResponseEntity<ConfiguracionEstacion> buscarPorEstacion(@PathVariable Long id) {
        return configuracionService.buscarPorEstacion(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/configuraciones/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ConfiguracionEstacion> buscarPorId(@PathVariable Long id) {
        return configuracionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/configuraciones
    @PostMapping
    public ResponseEntity<ConfiguracionEstacion> guardarConfiguracion(
            @Valid @RequestBody ConfiguracionEstacion configuracion) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(configuracionService.guardarConfiguracion(configuracion));
    }

    // PUT /api/configuraciones/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionEstacion> actualizarConfiguracion(
            @PathVariable Long id, @Valid @RequestBody ConfiguracionEstacion configuracion) {
        return configuracionService.actualizarConfiguracion(id, configuracion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/configuraciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConfiguracion(@PathVariable Long id) {
        return configuracionService.eliminarConfiguracion(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}