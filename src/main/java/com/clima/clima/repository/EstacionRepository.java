package com.clima.clima.repository;

import com.clima.clima.entity.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstacionRepository extends JpaRepository<Estacion, Long> {

    // GET /api/estaciones/ciudad/{id}
    List<Estacion> findByCiudadId(Long ciudadId);

    List<Estacion> findByEstado(String estado);

    boolean existsByCodigo(String codigo);

    // Para impedir borrar una ciudad que todavía tiene estaciones
    boolean existsByCiudadId(Long ciudadId);
}