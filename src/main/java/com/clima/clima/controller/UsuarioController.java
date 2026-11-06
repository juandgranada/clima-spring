package com.clima.clima.controller;

import com.clima.clima.entity.Usuario;
import com.clima.clima.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // GET /api/usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // GET /api/usuarios/rol/{nombre}  -> usuarios que tienen un rol, por ejemplo ADMIN
    @GetMapping("/rol/{nombre}")
    public ResponseEntity<List<Usuario>> listarPorRol(@PathVariable String nombre) {
        return ResponseEntity.ok(usuarioService.listarPorRol(nombre));
    }

    // GET /api/usuarios/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/usuarios
    @PostMapping
    public ResponseEntity<Usuario> guardarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.guardarUsuario(usuario));
    }

    // POST /api/usuarios/{id}/roles/{rolId}  -> asignar un rol al usuario
    @PostMapping("/{id}/roles/{rolId}")
    public ResponseEntity<Usuario> asignarRol(@PathVariable Long id, @PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioService.asignarRol(id, rolId));
    }

    // DELETE /api/usuarios/{id}/roles/{rolId}  -> quitar un rol al usuario
    @DeleteMapping("/{id}/roles/{rolId}")
    public ResponseEntity<Usuario> quitarRol(@PathVariable Long id, @PathVariable Long rolId) {
        return ResponseEntity.ok(usuarioService.quitarRol(id, rolId));
    }

    // PUT /api/usuarios/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return usuarioService.actualizarUsuario(id, usuario)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/usuarios/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        return usuarioService.eliminarUsuario(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}