package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class RolService {
    public List<Rol> findAllRol() {
        return null;
    }

    public Rol findByUserUnassignedRoles(Long id) {
        return null;
    }
}
