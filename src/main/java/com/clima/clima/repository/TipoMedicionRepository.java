package com.clima.clima.repository;

import com.clima.clima.entity.TipoMedicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoMedicionRepository extends JpaRepository<TipoMedicion, Long> {

    boolean existsByNombreIgnoreCase(String nombre);
}