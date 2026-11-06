package com.clima.clima.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estacion")
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre admite máximo 100 caracteres")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 30, message = "El código admite máximo 30 caracteres")
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @Size(max = 255, message = "La ubicación admite máximo 255 caracteres")
    @Column(length = 255)
    private String ubicacion;

    @Pattern(
        regexp = "ACTIVA|INACTIVA|MANTENIMIENTO",
        message = "El estado debe ser ACTIVA, INACTIVA o MANTENIMIENTO")
    @Column(nullable = false, length = 20)
    private String estado = "ACTIVA";

    // Relación N:1 con Ciudad (lado propietario: aquí está la FK)
    @NotNull(message = "La ciudad es obligatoria")
    @ManyToOne(optional = false)
    @JoinColumn(name = "ciudad_id", nullable = false)
    private Ciudad ciudad;

    // Relación 1:1 con ConfiguracionEstacion (lado inverso)
    @OneToOne(mappedBy = "estacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private ConfiguracionEstacion configuracion;

    // Relación 1:N con RegistroClima (lado inverso)
    @OneToMany(mappedBy = "estacion")
    @JsonIgnore
    private List<RegistroClima> registros = new ArrayList<>();

    public Estacion() {
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

    public ConfiguracionEstacion getConfiguracion() {
        return configuracion;
    }

    public List<RegistroClima> getRegistros() {
        return registros;
    }
}