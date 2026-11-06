package com.clima.clima.repository;

import com.clima.clima.entity.ConfiguracionEstacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfiguracionEstacionRepository extends JpaRepository<ConfiguracionEstacion, Long> {

    Optional<ConfiguracionEstacion> findByEstacionId(Long estacionId);

    // Relación 1:1: una estación solo puede tener una configuración
    boolean existsByEstacionId(Long estacionId);
}