package com.clima.clima.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "registro_clima")
public class RegistroClima {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal temperatura;

    private BigDecimal humedad;

    private BigDecimal presion;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "estacion_id")
    private Estacion estacion_id;

    public RegistroClima() {
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(BigDecimal temperatura) {
        this.temperatura = temperatura;
    }

    public BigDecimal getHumedad() {
        return humedad;
    }

    public void setHumedad(BigDecimal humedad) {
        this.humedad = humedad;
    }

    public BigDecimal getPresion() {
        return presion;
    }

    public void setPresion(BigDecimal presion) {
        this.presion = presion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Estacion getEstacion_id() {
        return estacion_id;
    }

    public void setEstacion_id(Estacion estacion_id) {
        this.estacion_id = estacion_id;
    }

}
