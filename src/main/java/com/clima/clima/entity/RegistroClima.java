package com.clima.clima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "registro_clima")
public class RegistroClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La temperatura es obligatoria")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal temperatura;

    @NotNull(message = "La humedad es obligatoria")
    @DecimalMin(value = "0.0", message = "La humedad no puede ser menor a 0")
    @DecimalMax(value = "100.0", message = "La humedad no puede ser mayor a 100")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal humedad;

    @NotNull(message = "La presión es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La presión debe ser mayor a 0")
    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal presion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    // Relación N:1 con Estacion (lado propietario)
    @NotNull(message = "La estación es obligatoria")
    @ManyToOne(optional = false)
    @JoinColumn(name = "estacion_id", nullable = false)
    private Estacion estacion;

    // Relación N:M con TipoMedicion (lado propietario, tabla registro_tipo_medicion)
    @ManyToMany
    @JoinTable(
        name = "registro_tipo_medicion",
        joinColumns = @JoinColumn(name = "registro_clima_id"),
        inverseJoinColumns = @JoinColumn(name = "tipo_medicion_id")
    )
    private Set<TipoMedicion> tiposMedicion = new HashSet<>();

    // Relación 1:N con Alerta (lado inverso)
    @OneToMany(mappedBy = "registroClima", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Alerta> alertas = new ArrayList<>();

    public RegistroClima() {
    }

    // Si la fecha no viene en el JSON, se asigna la fecha y hora actuales
    @PrePersist
    void asignarFecha() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }

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

    public Estacion getEstacion() {
        return estacion;
    }

    public void setEstacion(Estacion estacion) {
        this.estacion = estacion;
    }

    public Set<TipoMedicion> getTiposMedicion() {
        return tiposMedicion;
    }

    public void setTiposMedicion(Set<TipoMedicion> tiposMedicion) {
        this.tiposMedicion = tiposMedicion;
    }

    public List<Alerta> getAlertas() {
        return alertas;
    }
}