package com.clima.clima.service;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.repository.CiudadRepository;
import com.clima.clima.repository.EstacionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {

    private final CiudadRepository ciudadRepository;
    private final EstacionRepository estacionRepository;

    public CiudadService(CiudadRepository ciudadRepository, EstacionRepository estacionRepository) {
        this.ciudadRepository = ciudadRepository;
        this.estacionRepository = estacionRepository;
    }

    // Listar todas las ciudades
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    // Guardar una ciudad (409 si ya existe el mismo nombre en el mismo departamento)
    @Transactional
    public Ciudad guardarCiudad(Ciudad ciudad) {
        if (ciudadRepository.existsByNombreIgnoreCaseAndDepartamentoIgnoreCase(
                ciudad.getNombre(), ciudad.getDepartamento())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe la ciudad " + ciudad.getNombre()
                            + " en el departamento " + ciudad.getDepartamento());
        }
        ciudad.setId(null);
        return ciudadRepository.save(ciudad);
    }

    // Buscar una ciudad por ID
    public Optional<Ciudad> buscarPorId(Long id) {
        return ciudadRepository.findById(id);
    }

    // Actualizar una ciudad
    @Transactional
    public Optional<Ciudad> actualizarCiudad(Long id, Ciudad datos) {

        return ciudadRepository.findById(id).map(ciudad -> {

            boolean cambiaNombreODepartamento =
                    !ciudad.getNombre().equalsIgnoreCase(datos.getNombre())
                    || !ciudad.getDepartamento().equalsIgnoreCase(datos.getDepartamento());

            if (cambiaNombreODepartamento
                    && ciudadRepository.existsByNombreIgnoreCaseAndDepartamentoIgnoreCase(
                            datos.getNombre(), datos.getDepartamento())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe la ciudad " + datos.getNombre()
                                + " en el departamento " + datos.getDepartamento());
            }

            ciudad.setNombre(datos.getNombre());
            ciudad.setDepartamento(datos.getDepartamento());
            ciudad.setPoblacion(datos.getPoblacion());

            return ciudadRepository.save(ciudad);
        });
    }

    // Eliminar una ciudad (409 si todavía tiene estaciones)
    @Transactional
    public boolean eliminarCiudad(Long id) {

        if (!ciudadRepository.existsById(id)) {
            return false;
        }

        if (estacionRepository.existsByCiudadId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la ciudad porque tiene estaciones asociadas");
        }

        ciudadRepository.deleteById(id);
        return true;
    }
}