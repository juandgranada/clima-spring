package com.clima.clima.service;

import com.clima.clima.entity.Rol;
import com.clima.clima.entity.Usuario;
import com.clima.clima.repository.RolRepository;
import com.clima.clima.repository.UsuarioRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Listar los usuarios que tienen un rol (404 si el rol no existe)
    public List<Usuario> listarPorRol(String nombreRol) {
        if (!rolRepository.existsByNombreIgnoreCase(nombreRol)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Rol no encontrado con nombre " + nombreRol);
        }
        return usuarioRepository.findByRolesNombreIgnoreCase(nombreRol);
    }

    // Guardar un usuario (409 si el correo ya existe; los roles, si vienen, deben existir)
    @Transactional
    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un usuario con el correo " + usuario.getCorreo());
        }
        usuario.setId(null);
        usuario.setRoles(resolverRoles(usuario.getRoles()));
        return usuarioRepository.save(usuario);
    }

    // Buscar un usuario por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Actualizar un usuario. Los roles no se cambian aquí: se gestionan con asignarRol y quitarRol
    @Transactional
    public Optional<Usuario> actualizarUsuario(Long id, Usuario datos) {
        return usuarioRepository.findById(id).map(usuario -> {

            if (!usuario.getCorreo().equalsIgnoreCase(datos.getCorreo())
                    && usuarioRepository.existsByCorreo(datos.getCorreo())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe un usuario con el correo " + datos.getCorreo());
            }

            usuario.setNombre(datos.getNombre());
            usuario.setCorreo(datos.getCorreo());
            usuario.setContrasena(datos.getContrasena());
            return usuarioRepository.save(usuario);
        });
    }

    // Eliminar un usuario
    @Transactional
    public boolean eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            return false;
        }

        usuarioRepository.deleteById(id);
        return true;
    }

    // Asignar un rol a un usuario (404 si no existe el usuario o el rol; 409 si ya lo tiene)
    @Transactional
    public Usuario asignarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id " + usuarioId));
        Rol rol = rolRepository.findById(rolId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Rol no encontrado con id " + rolId));

        boolean yaTieneRol = usuario.getRoles().stream().anyMatch(r -> r.getId().equals(rolId));
        if (yaTieneRol) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "El usuario ya tiene asignado el rol " + rol.getNombre());
        }

        usuario.getRoles().add(rol);
        return usuarioRepository.save(usuario);
    }

    // Quitar un rol a un usuario (404 si no existe el usuario o no tiene ese rol)
    @Transactional
    public Usuario quitarRol(Long usuarioId, Long rolId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario no encontrado con id " + usuarioId));

        boolean eliminado = usuario.getRoles().removeIf(r -> r.getId().equals(rolId));
        if (!eliminado) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El usuario no tiene asignado el rol con id " + rolId);
        }
        return usuarioRepository.save(usuario);
    }

    // Convierte los roles del JSON ([{"id": 1}]) en entidades reales
    private Set<Rol> resolverRoles(Set<Rol> referencias) {
        Set<Rol> resueltos = new HashSet<>();
        if (referencias == null) {
            return resueltos;
        }
        for (Rol referencia : referencias) {
            if (referencia == null || referencia.getId() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Cada rol debe indicar su id");
            }
            Rol rol = rolRepository.findById(referencia.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Rol no encontrado con id " + referencia.getId()));
            resueltos.add(rol);
        }
        return resueltos;
    }
}