package com.clima.clima.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "estacion")
public class Estacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de la estación es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El código de la estación es obligatorio")
    @Size(max = 50, message = "El código no puede superar los 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @NotBlank(message = "La ubicación de la estación es obligatoria")
    @Size(max = 200, message = "La ubicación no puede superar los 200 caracteres")
    @Column(name = "ubicacion", length = 200)
    private String ubicacion;

    @NotBlank (message = "El estado de la estación es obligatorio")
    @Size(max = 50, message = "El estado no puede superar los 50 caracteres")
    @Column(name = "estado", length = 50)
    private String estado;


    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad;

    public Estacion() {
    }

    // Getters y Setters

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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

}
