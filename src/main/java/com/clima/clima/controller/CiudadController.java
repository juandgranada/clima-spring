package com.clima.clima.controller;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.service.CiudadService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudad")
@CrossOrigin (origins = "http://localhost:5173")
public class CiudadController {

    private final CiudadService CiudadService;

    public CiudadController(CiudadService CiudadService) {
        this.CiudadService = CiudadService;
    }

    @GetMapping
    public List<Ciudad> listarCiudads() {
        return CiudadService.listarCiudad();
    }

    @PostMapping
    public Ciudad guardarCiudad(@RequestBody Ciudad Ciudad) {
        return CiudadService.guardarCiudad(Ciudad);
    }

    @GetMapping("/{id}")
    public Ciudad buscarPorId(@PathVariable Long id) {
        return CiudadService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Ciudad actualizarCiudad(
            @PathVariable Long id,
            @RequestBody Ciudad Ciudad) {

        return CiudadService.actualizarCiudad(id, Ciudad);
    }

    @DeleteMapping("/{id}")
    public void eliminarCiudad(@PathVariable Long id) {
        CiudadService.eliminarCiudad(id);
    }
}