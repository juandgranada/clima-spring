package com.clima.clima.service;

import com.clima.clima.entity.Estacion;
import com.clima.clima.entity.RegistroClima;
import com.clima.clima.entity.TipoMedicion;
import com.clima.clima.repository.AlertaRepository;
import com.clima.clima.repository.CiudadRepository;
import com.clima.clima.repository.EstacionRepository;
import com.clima.clima.repository.RegistroClimaRepository;
import com.clima.clima.repository.TipoMedicionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RegistroClimaService {

    private final RegistroClimaRepository registroClimaRepository;
    private final EstacionRepository estacionRepository;
    private final CiudadRepository ciudadRepository;
    private final TipoMedicionRepository tipoMedicionRepository;
    private final AlertaRepository alertaRepository;

    public RegistroClimaService(RegistroClimaRepository registroClimaRepository,
                                EstacionRepository estacionRepository,
                                CiudadRepository ciudadRepository,
                                TipoMedicionRepository tipoMedicionRepository,
                                AlertaRepository alertaRepository) {
        this.registroClimaRepository = registroClimaRepository;
        this.estacionRepository = estacionRepository;
        this.ciudadRepository = ciudadRepository;
        this.tipoMedicionRepository = tipoMedicionRepository;
        this.alertaRepository = alertaRepository;
    }

    // Listar todos los registros de clima
    public List<RegistroClima> listarRegistroClima() {
        return registroClimaRepository.findAll();
    }

    // Listar las mediciones de una estación, las más recientes primero (404 si la estación no existe)
    public List<RegistroClima> listarPorEstacion(Long estacionId) {
        if (!estacionRepository.existsById(estacionId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Estación no encontrada con id " + estacionId);
        }
        return registroClimaRepository.findByEstacionIdOrderByFechaDesc(estacionId);
    }

    // Mediciones con temperatura superior a un valor (reto: más de 30 °C)
    public List<RegistroClima> listarConTemperaturaMayorA(BigDecimal temperatura) {
        return registroClimaRepository.findByTemperaturaGreaterThan(temperatura);
    }

    // Mediciones dentro de un rango de fechas
    public List<RegistroClima> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        if (desde.isAfter(hasta)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha 'desde' no puede ser posterior a 'hasta'");
        }
        return registroClimaRepository.findByFechaBetween(desde, hasta);
    }

    // Reto: temperatura promedio de una ciudad (404 si la ciudad no existe o no tiene mediciones)
    public Double obtenerTemperaturaPromedio(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ciudad no encontrada con id " + ciudadId);
        }
        Double promedio = registroClimaRepository.obtenerTemperaturaPromedio(ciudadId);
        if (promedio == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "La ciudad con id " + ciudadId + " aún no tiene mediciones");
        }
        return promedio;
    }

    // Guardar un registro de clima (la estación y los tipos de medición deben existir)
    @Transactional
    public RegistroClima guardarRegistroClima(RegistroClima registroClima) {
        registroClima.setId(null);
        registroClima.setEstacion(resolverEstacion(registroClima.getEstacion()));
        registroClima.setTiposMedicion(resolverTipos(registroClima.getTiposMedicion()));
        return registroClimaRepository.save(registroClima);
    }

    // Buscar un registro de clima por ID
    public Optional<RegistroClima> buscarPorId(Long id) {
        return registroClimaRepository.findById(id);
    }

    // Actualizar un registro de clima
    @Transactional
    public Optional<RegistroClima> actualizarRegistroClima(Long id, RegistroClima datos) {
        return registroClimaRepository.findById(id).map(registroClima -> {
            registroClima.setTemperatura(datos.getTemperatura());
            registroClima.setHumedad(datos.getHumedad());
            registroClima.setPresion(datos.getPresion());
            if (datos.getFecha() != null) {
                registroClima.setFecha(datos.getFecha());
            }
            registroClima.setEstacion(resolverEstacion(datos.getEstacion()));
            registroClima.setTiposMedicion(resolverTipos(datos.getTiposMedicion()));
            return registroClimaRepository.save(registroClima);
        });
    }

    // Eliminar un registro de clima (sus alertas se eliminan con él)
    @Transactional
    public boolean eliminarRegistroClima(Long id) {
        Optional<RegistroClima> registro = registroClimaRepository.findById(id);
        if (registro.isEmpty()) {
            return false;
        }

        alertaRepository.deleteAll(alertaRepository.findByRegistroClimaId(id));
        registroClimaRepository.delete(registro.get());
        return true;
    }

    // Convierte la referencia del JSON ("estacion": {"id": 1}) en la estación real
    private Estacion resolverEstacion(Estacion referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe indicar el id de la estación, por ejemplo \"estacion\": {\"id\": 1}");
        }
        return estacionRepository.findById(referencia.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Estación no encontrada con id " + referencia.getId()));
    }

    // Convierte los tipos del JSON ([{"id": 1}, {"id": 2}]) en entidades reales
    private Set<TipoMedicion> resolverTipos(Set<TipoMedicion> referencias) {
        Set<TipoMedicion> resueltos = new HashSet<>();
        if (referencias == null) {
            return resueltos;
        }
        for (TipoMedicion referencia : referencias) {
            if (referencia == null || referencia.getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cada tipo de medición debe indicar su id");
            }
            TipoMedicion tipo = tipoMedicionRepository.findById(referencia.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Tipo de medición no encontrado con id " + referencia.getId()));
            resueltos.add(tipo);
        }
        return resueltos;
    }
}