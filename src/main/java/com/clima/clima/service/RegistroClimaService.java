package com.clima.clima.service;

import com.clima.clima.entity.RegistroClima;
import com.clima.clima.repository.RegistroClimaRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroClimaService {
    private final RegistroClimaRepository RegistroClimaRepository;

    public RegistroClimaService(RegistroClimaRepository RegistroClimaRepository) {
        this.RegistroClimaRepository = RegistroClimaRepository;
    }

    // Listar todos los registros de clima
    public List<RegistroClima> listarRegistroClima() {
        return RegistroClimaRepository.findAll();
    }

    // Guardar un registro de clima
    public RegistroClima guardarRegistroClima(RegistroClima RegistroClima) {
        return RegistroClimaRepository.save(RegistroClima);
    }

    // Buscar un registro de clima por ID
    public Optional<RegistroClima> buscarPorId(Long id) {
        return RegistroClimaRepository.findById(id);
    }

    // Actualizar un registro de clima
    public Optional<RegistroClima> actualizarRegistroClima(Long id, RegistroClima datos) {
        return RegistroClimaRepository.findById(id).map(registroClima -> {
            registroClima.setTemperatura(datos.getTemperatura());
            registroClima.setHumedad(datos.getHumedad());
            registroClima.setPresion(datos.getPresion());
            registroClima.setFecha(datos.getFecha());
            registroClima.setEstacion(datos.getEstacion());
            return RegistroClimaRepository.save(registroClima);
        });
    }

    // Eliminar un registro de clima
    public boolean eliminarRegistroClima(Long id) {
        if (!RegistroClimaRepository.existsById(id)) {
            return false;
        }

        RegistroClimaRepository.deleteById(id);
        return true;
    }
}
