package com.kjvargas.admusers.SecurityJwt.SecuritySpring;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.SecurityJwt.Entitys.UsuarioSecurity;
import com.kjvargas.admusers.Services.Usuario.UsuarioRolService;
import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UsuarioRolService usuarioRolService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioSecurity usuario = this.usuarioRolService.findByIdEmailLoad(email);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        } else {
            return usuario;
        }
    }
}
