package com.clima.clima.repository;

import com.clima.clima.entity.RegistroClima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface RegistroClimaRepository extends JpaRepository<RegistroClima, Long> {

    // GET /api/mediciones/estacion/{id} (las más recientes primero)
    List<RegistroClima> findByEstacionIdOrderByFechaDesc(Long estacionId);

    // Consulta por temperatura (reto: mediciones con temperatura superior a 30)
    List<RegistroClima> findByTemperaturaGreaterThan(BigDecimal temperatura);

    // Consulta por fecha
    List<RegistroClima> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    // Para impedir borrar una estación que todavía tiene mediciones
    boolean existsByEstacionId(Long estacionId);

    // Reto: temperatura promedio de una ciudad
    @Query("""
        SELECT AVG(r.temperatura)
        FROM RegistroClima r
        WHERE r.estacion.ciudad.id = :ciudadId
    """)
    Double obtenerTemperaturaPromedio(@Param("ciudadId") Long ciudadId);
}