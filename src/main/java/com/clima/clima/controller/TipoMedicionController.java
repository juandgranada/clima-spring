package com.clima.clima.controller;

import com.clima.clima.entity.TipoMedicion;
import com.clima.clima.service.TipoMedicionService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tipos-medicion")
@CrossOrigin(origins = "http://localhost:5173")
public class TipoMedicionController {

    private final TipoMedicionService tipoMedicionService;

    public TipoMedicionController(TipoMedicionService tipoMedicionService) {
        this.tipoMedicionService = tipoMedicionService;
    }

    // GET /api/tipos-medicion
    @GetMapping
    public ResponseEntity<List<TipoMedicion>> listarTiposMedicion() {
        return ResponseEntity.ok(tipoMedicionService.listarTiposMedicion());
    }

    // GET /api/tipos-medicion/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TipoMedicion> buscarPorId(@PathVariable Long id) {
        return tipoMedicionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/tipos-medicion
    @PostMapping
    public ResponseEntity<TipoMedicion> guardarTipoMedicion(@Valid @RequestBody TipoMedicion tipo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoMedicionService.guardarTipoMedicion(tipo));
    }

    // PUT /api/tipos-medicion/{id}
    @PutMapping("/{id}")
    public ResponseEntity<TipoMedicion> actualizarTipoMedicion(
            @PathVariable Long id, @Valid @RequestBody TipoMedicion tipo) {
        return tipoMedicionService.actualizarTipoMedicion(id, tipo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/tipos-medicion/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTipoMedicion(@PathVariable Long id) {
        return tipoMedicionService.eliminarTipoMedicion(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}