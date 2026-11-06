package com.clima.clima.service;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.entity.Estacion;
import com.clima.clima.repository.CiudadRepository;
import com.clima.clima.repository.ConfiguracionEstacionRepository;
import com.clima.clima.repository.EstacionRepository;
import com.clima.clima.repository.RegistroClimaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EstacionService {

    private final EstacionRepository estacionRepository;
    private final CiudadRepository ciudadRepository;
    private final RegistroClimaRepository registroClimaRepository;
    private final ConfiguracionEstacionRepository configuracionRepository;

    public EstacionService(EstacionRepository estacionRepository,
                           CiudadRepository ciudadRepository,
                           RegistroClimaRepository registroClimaRepository,
                           ConfiguracionEstacionRepository configuracionRepository) {
        this.estacionRepository = estacionRepository;
        this.ciudadRepository = ciudadRepository;
        this.registroClimaRepository = registroClimaRepository;
        this.configuracionRepository = configuracionRepository;
    }

    // Listar todas las estaciones
    public List<Estacion> listarEstaciones() {
        return estacionRepository.findAll();
    }

    // Listar las estaciones de una ciudad (404 si la ciudad no existe)
    public List<Estacion> listarPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ciudad no encontrada con id " + ciudadId);
        }
        return estacionRepository.findByCiudadId(ciudadId);
    }

    // Guardar una estación (la ciudad debe existir y el código no puede repetirse)
    @Transactional
    public Estacion guardarEstacion(Estacion estacion) {
        if (estacionRepository.existsByCodigo(estacion.getCodigo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe una estación con el código " + estacion.getCodigo());
        }
        estacion.setId(null);
        estacion.setCiudad(resolverCiudad(estacion.getCiudad()));
        return estacionRepository.save(estacion);
    }

    // Buscar una estación por ID
    public Optional<Estacion> buscarPorId(Long id) {
        return estacionRepository.findById(id);
    }

    // Actualizar una estación
    @Transactional
    public Optional<Estacion> actualizarEstacion(Long id, Estacion datos) {
        return estacionRepository.findById(id).map(estacion -> {

            if (!estacion.getCodigo().equals(datos.getCodigo())
                    && estacionRepository.existsByCodigo(datos.getCodigo())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe una estación con el código " + datos.getCodigo());
            }

            estacion.setNombre(datos.getNombre());
            estacion.setCodigo(datos.getCodigo());
            estacion.setUbicacion(datos.getUbicacion());
            estacion.setEstado(datos.getEstado());
            estacion.setCiudad(resolverCiudad(datos.getCiudad()));
            return estacionRepository.save(estacion);
        });
    }

    // Eliminar una estación (409 si tiene mediciones; su configuración se elimina con ella)
    @Transactional
    public boolean eliminarEstacion(Long id) {
        Optional<Estacion> estacion = estacionRepository.findById(id);
        if (estacion.isEmpty()) {
            return false;
        }

        if (registroClimaRepository.existsByEstacionId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la estación porque tiene mediciones registradas");
        }

        configuracionRepository.findByEstacionId(id).ifPresent(configuracionRepository::delete);
        estacionRepository.delete(estacion.get());
        return true;
    }

    // Convierte la referencia del JSON ("ciudad": {"id": 1}) en la ciudad real de la base de datos
    private Ciudad resolverCiudad(Ciudad referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe indicar el id de la ciudad, por ejemplo \"ciudad\": {\"id\": 1}");
        }
        return ciudadRepository.findById(referencia.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Ciudad no encontrada con id " + referencia.getId()));
    }
}