package com.clima.clima.service;

import com.clima.clima.entity.ConfiguracionEstacion;
import com.clima.clima.entity.Estacion;
import com.clima.clima.repository.ConfiguracionEstacionRepository;
import com.clima.clima.repository.EstacionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionEstacionService {

    private final ConfiguracionEstacionRepository configuracionRepository;
    private final EstacionRepository estacionRepository;

    public ConfiguracionEstacionService(ConfiguracionEstacionRepository configuracionRepository,
                                        EstacionRepository estacionRepository) {
        this.configuracionRepository = configuracionRepository;
        this.estacionRepository = estacionRepository;
    }

    // Listar todas las configuraciones
    public List<ConfiguracionEstacion> listarConfiguraciones() {
        return configuracionRepository.findAll();
    }

    // Guardar una configuración (relación 1:1: una estación solo puede tener una)
    @Transactional
    public ConfiguracionEstacion guardarConfiguracion(ConfiguracionEstacion configuracion) {
        Estacion estacion = resolverEstacion(configuracion.getEstacion());

        if (configuracionRepository.existsByEstacionId(estacion.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "La estación con id " + estacion.getId() + " ya tiene una configuración");
        }

        configuracion.setId(null);
        configuracion.setEstacion(estacion);
        return configuracionRepository.save(configuracion);
    }

    // Buscar una configuración por ID
    public Optional<ConfiguracionEstacion> buscarPorId(Long id) {
        return configuracionRepository.findById(id);
    }

    // Buscar la configuración de una estación
    public Optional<ConfiguracionEstacion> buscarPorEstacion(Long estacionId) {
        return configuracionRepository.findByEstacionId(estacionId);
    }

    // Actualizar una configuración (no se puede mover a otra estación)
    @Transactional
    public Optional<ConfiguracionEstacion> actualizarConfiguracion(Long id, ConfiguracionEstacion datos) {
        return configuracionRepository.findById(id).map(configuracion -> {

            Estacion referencia = datos.getEstacion();
            if (referencia != null && referencia.getId() != null
                    && !referencia.getId().equals(configuracion.getEstacion().getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "No se puede cambiar la estación de una configuración existente");
            }

            configuracion.setIntervaloMedicionMin(datos.getIntervaloMedicionMin());
            configuracion.setUmbralTempMax(datos.getUmbralTempMax());
            configuracion.setUmbralPresionMin(datos.getUmbralPresionMin());
            return configuracionRepository.save(configuracion);
        });
    }

    // Eliminar una configuración
    @Transactional
    public boolean eliminarConfiguracion(Long id) {
        if (!configuracionRepository.existsById(id)) {
            return false;
        }

        configuracionRepository.deleteById(id);
        return true;
    }

    private Estacion resolverEstacion(Estacion referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe indicar el id de la estación, por ejemplo \"estacion\": {\"id\": 1}");
        }
        return estacionRepository.findById(referencia.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Estación no encontrada con id " + referencia.getId()));
    }
}