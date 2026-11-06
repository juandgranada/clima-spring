package com.clima.clima.service;

import com.clima.clima.entity.Estacion;
import com.clima.clima.repository.EstacionRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EstacionService {
    private final EstacionRepository EstacionRepository;

    public EstacionService(EstacionRepository EstacionRepository) {
        this.EstacionRepository = EstacionRepository;
    }

    public List<Estacion> listarEstacion() {
        return EstacionRepository.findAll();
    }

    public Estacion guardarEstacion(Estacion Estacion) {
        return EstacionRepository.save(Estacion);
    }

    public Estacion buscarPorId(Long id) {
        return EstacionRepository.findById(id)
                .orElse(null);
    }

    public Estacion actualizarEstacion(Long id, Estacion datos) {
        Estacion Estacion = EstacionRepository.findById(id)
                .orElse(null);
        if (Estacion == null) {
            return null;
        }
        Estacion.setNombre(datos.getNombre());
        Estacion.setCodigo(datos.getCodigo());
        Estacion.setUbicacion(datos.getUbicacion());
        Estacion.setEstado(datos.getEstado());
        Estacion.setCiudad_id(datos.getCiudad_id());
        return EstacionRepository.save(Estacion);
    }

    public void eliminarEstacion(Long id) {
        EstacionRepository.deleteById(id);
    }
}
