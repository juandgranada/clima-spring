package com.clima.clima.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracion_estacion")
public class ConfiguracionEstacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación 1:1 con Estacion (lado propietario: la FK es UNIQUE)
    @NotNull(message = "La estación es obligatoria")
    @OneToOne(optional = false)
    @JoinColumn(name = "estacion_id", nullable = false, unique = true)
    private Estacion estacion;

    @NotNull(message = "El intervalo de medición es obligatorio")
    @Min(value = 1, message = "El intervalo debe ser de al menos 1 minuto")
    @Column(name = "intervalo_medicion_min", nullable = false)
    private Integer intervaloMedicionMin = 10;

    @Column(name = "umbral_temp_max", precision = 5, scale = 2)
    private BigDecimal umbralTempMax;

    @Column(name = "umbral_presion_min", precision = 7, scale = 2)
    private BigDecimal umbralPresionMin;

    public ConfiguracionEstacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Integer getIntervaloMedicionMin() {
        return intervaloMedicionMin;
    }

    public void setIntervaloMedicionMin(Integer intervaloMedicionMin) {
        this.intervaloMedicionMin = intervaloMedicionMin;
    }

    public BigDecimal getUmbralTempMax() {
        return umbralTempMax;
    }

    public void setUmbralTempMax(BigDecimal umbralTempMax) {
        this.umbralTempMax = umbralTempMax;
    }

    public BigDecimal getUmbralPresionMin() {
        return umbralPresionMin;
    }

    public void setUmbralPresionMin(BigDecimal umbralPresionMin) {
        this.umbralPresionMin = umbralPresionMin;
    }
}