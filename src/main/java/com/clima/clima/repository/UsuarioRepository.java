package com.clima.clima.repository;

import com.clima.clima.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // GET /api/usuarios/rol/{nombre} (recorre la colección roles y filtra por rol.nombre)
    List<Usuario> findByRolesNombreIgnoreCase(String nombre);

    Optional<Usuario> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}