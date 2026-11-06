package com.clima.clima.service;

import com.clima.clima.entity.Alerta;
import com.clima.clima.entity.RegistroClima;
import com.clima.clima.repository.AlertaRepository;
import com.clima.clima.repository.CiudadRepository;
import com.clima.clima.repository.RegistroClimaRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
public class AlertaService {

    private static final Set<String> NIVELES = Set.of("BAJO", "MEDIO", "ALTO");

    private final AlertaRepository alertaRepository;
    private final RegistroClimaRepository registroClimaRepository;
    private final CiudadRepository ciudadRepository;

    public AlertaService(AlertaRepository alertaRepository,
                         RegistroClimaRepository registroClimaRepository,
                         CiudadRepository ciudadRepository) {
        this.alertaRepository = alertaRepository;
        this.registroClimaRepository = registroClimaRepository;
        this.ciudadRepository = ciudadRepository;
    }

    // Listar todas las alertas
    public List<Alerta> listarAlertas() {
        return alertaRepository.findAll();
    }

    // Listar alertas por nivel: BAJO, MEDIO o ALTO (400 si el nivel no es válido)
    public List<Alerta> listarPorNivel(String nivel) {
        String nivelNormalizado = nivel.toUpperCase(Locale.ROOT);
        if (!NIVELES.contains(nivelNormalizado)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nivel debe ser BAJO, MEDIO o ALTO");
        }
        return alertaRepository.findByNivelIgnoreCase(nivelNormalizado);
    }

    // Reto: alertas activas de una ciudad (404 si la ciudad no existe)
    public List<Alerta> listarActivasPorCiudad(Long ciudadId) {
        if (!ciudadRepository.existsById(ciudadId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ciudad no encontrada con id " + ciudadId);
        }
        return alertaRepository.obtenerAlertasActivasPorCiudad(ciudadId);
    }

    // Guardar una alerta (el registro de clima debe existir)
    @Transactional
    public Alerta guardarAlerta(Alerta alerta) {
        alerta.setId(null);
        alerta.setRegistroClima(resolverRegistro(alerta.getRegistroClima()));
        return alertaRepository.save(alerta);
    }

    // Buscar una alerta por ID
    public Optional<Alerta> buscarPorId(Long id) {
        return alertaRepository.findById(id);
    }

    // Actualizar una alerta
    @Transactional
    public Optional<Alerta> actualizarAlerta(Long id, Alerta datos) {
        return alertaRepository.findById(id).map(alerta -> {
            alerta.setDescripcion(datos.getDescripcion());
            alerta.setNivel(datos.getNivel());
            if (datos.getEstado() != null) {
                alerta.setEstado(datos.getEstado());
            }
            if (datos.getFecha() != null) {
                alerta.setFecha(datos.getFecha());
            }
            alerta.setRegistroClima(resolverRegistro(datos.getRegistroClima()));
            return alertaRepository.save(alerta);
        });
    }

    // Eliminar una alerta
    @Transactional
    public boolean eliminarAlerta(Long id) {
        if (!alertaRepository.existsById(id)) {
            return false;
        }

        alertaRepository.deleteById(id);
        return true;
    }

    private RegistroClima resolverRegistro(RegistroClima referencia) {
        if (referencia == null || referencia.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Debe indicar el id del registro de clima, por ejemplo \"registroClima\": {\"id\": 1}");
        }
        return registroClimaRepository.findById(referencia.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registro de clima no encontrado con id " + referencia.getId()));
    }
}