package com.clima.clima.repository;

import com.clima.clima.entity.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    // GET /api/alertas/nivel/{nivel}
    List<Alerta> findByNivelIgnoreCase(String nivel);

    List<Alerta> findByEstado(String estado);

    List<Alerta> findByRegistroClimaId(Long registroClimaId);

    // Reto: alertas activas de una ciudad determinada
    @Query("""
        SELECT a
        FROM Alerta a
        WHERE a.estado = 'ACTIVA'
          AND a.registroClima.estacion.ciudad.id = :ciudadId
        ORDER BY a.fecha DESC
    """)
    List<Alerta> obtenerAlertasActivasPorCiudad(@Param("ciudadId") Long ciudadId);
}