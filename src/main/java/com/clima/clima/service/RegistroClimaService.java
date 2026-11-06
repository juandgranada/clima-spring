package com.clima.clima.service;

import com.clima.clima.entity.RegistroClima;
import com.clima.clima.repository.RegistroClimaRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistroClimaService {
    private final RegistroClimaRepository RegistroClimaRepository;

    public RegistroClimaService(RegistroClimaRepository RegistroClimaRepository) {
        this.RegistroClimaRepository = RegistroClimaRepository;
    }

    public List<RegistroClima> listarRegistroClima() {
        return RegistroClimaRepository.findAll();
    }

    public RegistroClima guardarRegistroClima(RegistroClima RegistroClima) {
        return RegistroClimaRepository.save(RegistroClima);
    }

    public RegistroClima buscarPorId(Long id) {
        return RegistroClimaRepository.findById(id)
                .orElse(null);
    }

    public RegistroClima actualizarRegistroClima(Long id, RegistroClima datos) {
        RegistroClima RegistroClima = RegistroClimaRepository.findById(id)
                .orElse(null);
        if (RegistroClima == null) {
            return null;
        }
        RegistroClima.setTemperatura(datos.getTemperatura());
        RegistroClima.setHumedad(datos.getHumedad());
        RegistroClima.setPresion(datos.getPresion());
        RegistroClima.setFecha(datos.getFecha());
        RegistroClima.setEstacion_id(datos.getEstacion_id());
        return RegistroClimaRepository.save(RegistroClima);
    }

    public void eliminarRegistroClima(Long id) {
        RegistroClimaRepository.deleteById(id);
    }
}
