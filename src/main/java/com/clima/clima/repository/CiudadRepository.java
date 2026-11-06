package com.clima.clima.repository;

import com.clima.clima.entity.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    // Para evitar duplicados (nombre + departamento) y responder 409 en lugar de 500
    boolean existsByNombreIgnoreCaseAndDepartamentoIgnoreCase(String nombre, String departamento);
}