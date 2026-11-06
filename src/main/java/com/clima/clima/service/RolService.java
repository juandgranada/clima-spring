package com.clima.clima.service;

import com.clima.clima.entity.Rol;
import com.clima.clima.repository.RolRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Listar todos los roles
    public List<Rol> listarRoles() {
        return rolRepository.findAll();
    }

    // Guardar un rol (409 si el nombre ya existe)
    @Transactional
    public Rol guardarRol(Rol rol) {
        if (rolRepository.existsByNombreIgnoreCase(rol.getNombre())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Ya existe un rol llamado " + rol.getNombre());
        }
        rol.setId(null);
        return rolRepository.save(rol);
    }

    // Buscar un rol por ID
    public Optional<Rol> buscarPorId(Long id) {
        return rolRepository.findById(id);
    }

    // Actualizar un rol
    @Transactional
    public Optional<Rol> actualizarRol(Long id, Rol datos) {
        return rolRepository.findById(id).map(rol -> {

            if (!rol.getNombre().equalsIgnoreCase(datos.getNombre())
                    && rolRepository.existsByNombreIgnoreCase(datos.getNombre())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,
                        "Ya existe un rol llamado " + datos.getNombre());
            }

            rol.setNombre(datos.getNombre());
            rol.setDescripcion(datos.getDescripcion());
            return rolRepository.save(rol);
        });
    }

    // Eliminar un rol (409 si está asignado a algún usuario)
    @Transactional
    public boolean eliminarRol(Long id) {
        Optional<Rol> rol = rolRepository.findById(id);
        if (rol.isEmpty()) {
            return false;
        }

        if (!rol.get().getUsuarios().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el rol porque está asignado a uno o más usuarios");
        }

        rolRepository.delete(rol.get());
        return true;
    }
}