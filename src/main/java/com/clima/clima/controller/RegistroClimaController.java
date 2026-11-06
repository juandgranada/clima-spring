package com.clima.clima.controller;

import com.clima.clima.entity.RegistroClima;
import com.clima.clima.service.RegistroClimaService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registro-clima")
@CrossOrigin (origins = "http://localhost:5173")
public class RegistroClimaController {

    private final RegistroClimaService RegistroClimaService;

    public RegistroClimaController(RegistroClimaService RegistroClimaService) {
        this.RegistroClimaService = RegistroClimaService;
    }

    @GetMapping
    public List<RegistroClima> listarRegistroClimas() {
        return RegistroClimaService.listarRegistroClima();
    }

    @PostMapping
    public RegistroClima guardarRegistroClima(@RequestBody RegistroClima RegistroClima) {
        return RegistroClimaService.guardarRegistroClima(RegistroClima);
    }

    @GetMapping("/{id}")
    public RegistroClima buscarPorId(@PathVariable Long id) {
        return RegistroClimaService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public RegistroClima actualizarRegistroClima(
            @PathVariable Long id,
            @RequestBody RegistroClima RegistroClima) {

        return RegistroClimaService.actualizarRegistroClima(id, RegistroClima);
    }

    @DeleteMapping("/{id}")
    public void eliminarRegistroClima(@PathVariable Long id) {
        RegistroClimaService.eliminarRegistroClima(id);
    }
}