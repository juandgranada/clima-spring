package com.clima.clima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "ciudad",
    uniqueConstraints = @UniqueConstraint(
        name = "uq_ciudad_nombre_depto",
        columnNames = {"nombre", "departamento"})
)
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El departamento es obligatorio")
    @Size(max = 100, message = "El departamento admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String departamento;

    @Min(value = 0, message = "La población no puede ser negativa")
    private Integer poblacion;

    // Relación 1:N con Estacion (lado inverso, la FK vive en estacion.ciudad_id)
    @OneToMany(mappedBy = "ciudad")
    @JsonIgnore
    private List<Estacion> estaciones = new ArrayList<>();

    public Ciudad() {
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

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Integer getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Integer poblacion) {
        this.poblacion = poblacion;
    }

    public List<Estacion> getEstaciones() {
        return estaciones;
    }
}