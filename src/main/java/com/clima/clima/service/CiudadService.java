
package com.clima.clima.service;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.repository.CiudadRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CiudadService {

    private final CiudadRepository ciudadRepository;

    public CiudadService(CiudadRepository ciudadRepository) {
        this.ciudadRepository = ciudadRepository;
    }

    // Listar todas las ciudades
    public List<Ciudad> listarCiudades() {
        return ciudadRepository.findAll();
    }

    // Guardar una ciudad
    public Ciudad guardarCiudad(Ciudad ciudad) {
        return ciudadRepository.save(ciudad);
    }

    // Buscar una ciudad por ID
    public Optional<Ciudad> buscarPorId(Long id) {
        return ciudadRepository.findById(id);
    }

    // Actualizar una ciudad
    public Optional<Ciudad> actualizarCiudad(Long id, Ciudad datos) {

        return ciudadRepository.findById(id).map(ciudad -> {
            ciudad.setNombre(datos.getNombre());
            ciudad.setDepartamento(datos.getDepartamento());
            ciudad.setPoblacion(datos.getPoblacion());

            return ciudadRepository.save(ciudad);
        });
    }

    // Eliminar una ciudad
    public boolean eliminarCiudad(Long id) {

        if (!ciudadRepository.existsById(id)) {
            return false;
        }

        ciudadRepository.deleteById(id);
        return true;
    }
}
