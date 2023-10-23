package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Repositories.UsuarioRolRepository;
import com.kjvargas.admusers.SecurityJwt.Entitys.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UsuarioRolService {
    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    public List<Rol> findByUserUnassignedRoles(Long id) {
        List<Rol> roles = this.usuarioRolRepository.findByUserUnassignedRoles(id);
        return roles;
    }

    public List<Rol> findRolByUserId(Long id) {
        List<Rol> roles = this.usuarioRolRepository.findRolByUserId(id);
        return roles;
    }

    public Usuario findByIdUser(Long id) {
        Usuario usuario = usuarioRolRepository.findByIdUser(id);
        return usuario;
    }

    public Usuario updateRolUser(Long id_rol, Long id_user) {
        Usuario usuario = usuarioRolRepository.updateRolUser(id_rol, id_user);
        return usuario;
    }

    public Usuario findByIdEmail(String email) {
        Usuario usuario = usuarioRolRepository.findByIdEmail(email);
        return usuario;
    }

    public UsuarioSecurity findByIdEmailLoad(String email) {
        UsuarioSecurity usuario = usuarioRolRepository.findByIdEmailLoad(email);
        return usuario;
    }

}
