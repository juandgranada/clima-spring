package com.clima.clima.service;

import com.clima.clima.entity.Estacion;
import com.clima.clima.repository.EstacionRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EstacionService {
    private final EstacionRepository EstacionRepository;

    public EstacionService(EstacionRepository EstacionRepository) {
        this.EstacionRepository = EstacionRepository;
    }

    // Listar todas las estaciones
    public List<Estacion> listarEstaciones() {
        return EstacionRepository.findAll();
    }

    // Guardar una estación
    public Estacion guardarEstacion(Estacion Estacion) {
        return EstacionRepository.save(Estacion);
    }

    // Buscar una estación por ID
    public Optional<Estacion> buscarPorId(Long id) {
        return EstacionRepository.findById(id);
    }

    // Actualizar una estación
    public Optional<Estacion> actualizarEstacion(Long id, Estacion datos) {
        return EstacionRepository.findById(id).map(estacion -> {
            estacion.setNombre(datos.getNombre());
            estacion.setCodigo(datos.getCodigo());
            estacion.setUbicacion(datos.getUbicacion());
            estacion.setEstado(datos.getEstado());
            estacion.setCiudad(datos.getCiudad());
            return EstacionRepository.save(estacion);
        });
    }

    // Eliminar una estación
    public boolean eliminarEstacion(Long id) {
        if (!EstacionRepository.existsById(id)) {
            return false;
        }

        EstacionRepository.deleteById(id);
        return true;
    }
}
