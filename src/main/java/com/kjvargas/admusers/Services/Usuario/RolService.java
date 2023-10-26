package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Repositories.RolRepository;
import com.kjvargas.admusers.Repositories.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    @Autowired
    UsuarioRolRepository usuarioRolRepository;

    public List<Rol> findAllRoles() {
        List<Rol> roles = rolRepository.findAllRoles();
        if (roles.isEmpty()) {
            throw new RuntimeException("No hay roles");
        }
        return roles;
    }
}
