package com.clima.clima.controller;

import com.clima.clima.entity.Rol;
import com.clima.clima.service.RolService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "http://localhost:5173")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    // GET /api/roles
    @GetMapping
    public ResponseEntity<List<Rol>> listarRoles() {
        return ResponseEntity.ok(rolService.listarRoles());
    }

    // GET /api/roles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Rol> buscarPorId(@PathVariable Long id) {
        return rolService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/roles
    @PostMapping
    public ResponseEntity<Rol> guardarRol(@Valid @RequestBody Rol rol) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rolService.guardarRol(rol));
    }

    // PUT /api/roles/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizarRol(@PathVariable Long id, @Valid @RequestBody Rol rol) {
        return rolService.actualizarRol(id, rol)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/roles/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Long id) {
        return rolService.eliminarRol(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}