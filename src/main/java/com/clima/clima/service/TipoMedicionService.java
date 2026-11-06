package com.clima.clima.service;

import com.clima.clima.entity.TipoMedicion;
import com.clima.clima.repository.TipoMedicionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class TipoMedicionService {

    private final TipoMedicionRepository tipoMedicionRepository;

    public TipoMedicionService(TipoMedicionRepository tipoMedicionRepository) {
        this.tipoMedicionRepository = tipoMedicionRepository;
    }

    // Listar todos los tipos de medición
    public List<TipoMedicion> listarTiposMedicion() {
        return tipoMedicionRepository.findAll();
    }

    // Guardar un tipo de medición (409 si el nombre ya existe)
    @Transactional
    public TipoMedicion guardarTipoMedicion(TipoMedicion tipo) {
        if (tipoMedicionRepository.existsByNombreIgnoreCase(tipo.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un tipo de medición llamado " + tipo.getNombre());
        }
        tipo.setId(null);
        return tipoMedicionRepository.save(tipo);
    }

    // Buscar un tipo de medición por ID
    public Optional<TipoMedicion> buscarPorId(Long id) {
        return tipoMedicionRepository.findById(id);
    }

    // Actualizar un tipo de medición
    @Transactional
    public Optional<TipoMedicion> actualizarTipoMedicion(Long id, TipoMedicion datos) {
        return tipoMedicionRepository.findById(id).map(tipo -> {

            if (!tipo.getNombre().equalsIgnoreCase(datos.getNombre())
                    && tipoMedicionRepository.existsByNombreIgnoreCase(datos.getNombre())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe un tipo de medición llamado " + datos.getNombre());
            }

            tipo.setNombre(datos.getNombre());
            tipo.setUnidad(datos.getUnidad());
            tipo.setDescripcion(datos.getDescripcion());
            return tipoMedicionRepository.save(tipo);
        });
    }

    // Eliminar un tipo de medición (409 si está asociado a registros de clima)
    @Transactional
    public boolean eliminarTipoMedicion(Long id) {
        Optional<TipoMedicion> tipo = tipoMedicionRepository.findById(id);
        if (tipo.isEmpty()) {
            return false;
        }

        if (!tipo.get().getRegistros().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el tipo de medición porque está asociado a registros de clima");
        }

        tipoMedicionRepository.delete(tipo.get());
        return true;
    }
}