package com.clima.clima.service;

import com.clima.clima.entity.Ciudad;
import com.clima.clima.repository.CiudadRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CiudadService {
    private final CiudadRepository CiudadRepository;

    public CiudadService(CiudadRepository CiudadRepository) {
        this.CiudadRepository = CiudadRepository;
    }

    public List<Ciudad> listarCiudad() {
        return CiudadRepository.findAll();
    }

    public Ciudad guardarCiudad(Ciudad Ciudad) {
        return CiudadRepository.save(Ciudad);
    }

    public Ciudad buscarPorId(Long id) {
        return CiudadRepository.findById(id)
                .orElse(null);
    }

    public Ciudad actualizarCiudad(Long id, Ciudad datos) {
        Ciudad Ciudad = CiudadRepository.findById(id)
                .orElse(null);
        if (Ciudad == null) {
            return null;
        }
        Ciudad.setNombre(datos.getNombre());
        Ciudad.setDepartamento(datos.getDepartamento());
        Ciudad.setPoblacion(datos.getPoblacion());
        return CiudadRepository.save(Ciudad);
    }

    public void eliminarCiudad(Long id) {
        CiudadRepository.deleteById(id);
    }
}
