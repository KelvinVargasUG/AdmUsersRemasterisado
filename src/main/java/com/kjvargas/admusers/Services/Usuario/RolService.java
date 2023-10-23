package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Repositories.RolRepository;
import com.kjvargas.admusers.Repositories.UsuarioRolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
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
