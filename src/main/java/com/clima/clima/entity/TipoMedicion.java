package com.clima.clima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tipo_medicion")
public class TipoMedicion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre admite máximo 50 caracteres")
    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @NotBlank(message = "La unidad es obligatoria")
    @Size(max = 20, message = "La unidad admite máximo 20 caracteres")
    @Column(nullable = false, length = 20)
    private String unidad;

    @Size(max = 255, message = "La descripción admite máximo 255 caracteres")
    @Column(length = 255)
    private String descripcion;

    // Relación N:M con RegistroClima (lado inverso)
    @ManyToMany(mappedBy = "tiposMedicion")
    @JsonIgnore
    private Set<RegistroClima> registros = new HashSet<>();

    public TipoMedicion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<RegistroClima> getRegistros() {
        return registros;
    }
}