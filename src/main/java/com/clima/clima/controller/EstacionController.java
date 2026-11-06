package com.clima.clima.controller;

import com.clima.clima.entity.Estacion;
import com.clima.clima.service.EstacionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estacion")
@CrossOrigin (origins = "http://localhost:5173")
public class EstacionController {

    private final EstacionService EstacionService;

    public EstacionController(EstacionService EstacionService) {
        this.EstacionService = EstacionService;
    }

    @GetMapping
    public List<Estacion> listarEstacions() {
        return EstacionService.listarEstacion();
    }

    @PostMapping
    public Estacion guardarEstacion(@RequestBody Estacion Estacion) {
        return EstacionService.guardarEstacion(Estacion);
    }

    @GetMapping("/{id}")
    public Estacion buscarPorId(@PathVariable Long id) {
        return EstacionService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Estacion actualizarEstacion(
            @PathVariable Long id,
            @RequestBody Estacion Estacion) {

        return EstacionService.actualizarEstacion(id, Estacion);
    }

    @DeleteMapping("/{id}")
    public void eliminarEstacion(@PathVariable Long id) {
        EstacionService.eliminarEstacion(id);
    }
}