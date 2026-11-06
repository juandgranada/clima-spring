package com.clima.clima.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción admite máximo 255 caracteres")
    @Column(nullable = false, length = 255)
    private String descripcion;

    @NotBlank(message = "El nivel es obligatorio")
    @Pattern(regexp = "BAJO|MEDIO|ALTO", message = "El nivel debe ser BAJO, MEDIO o ALTO")
    @Column(nullable = false, length = 10)
    private String nivel;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Pattern(regexp = "ACTIVA|RESUELTA", message = "El estado debe ser ACTIVA o RESUELTA")
    @Column(nullable = false, length = 10)
    private String estado = "ACTIVA";

    // Relación N:1 con RegistroClima (lado propietario)
    @NotNull(message = "El registro de clima es obligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "registro_clima_id", nullable = false)
    private RegistroClima registroClima;

    public Alerta() {
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public RegistroClima getRegistroClima() {
        return registroClima;
    }

    public void setRegistroClima(RegistroClima registroClima) {
        this.registroClima = registroClima;
    }
}