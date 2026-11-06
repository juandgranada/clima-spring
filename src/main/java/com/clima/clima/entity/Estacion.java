package com.clima.clima.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "estacion")
public class Estacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String codigo;

    private String ubicacion;

    private String estado;

    @ManyToOne
    @JoinColumn(name = "ciudad_id")
    private Ciudad ciudad_id;

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

    public Ciudad getCiudad_id() {
        return ciudad_id;
    }

    public void setCiudad_id(Ciudad ciudad_id) {
        this.ciudad_id = ciudad_id;
    }
}
