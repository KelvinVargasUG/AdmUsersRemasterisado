package com.kjvargas.admusers.SecurityJwt.Entitys;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UsuarioSecurity implements UserDetails {

    private String email;

    private String password;

    private List<Rol> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        for (Rol rol : this.roles) {
            autoridades.add(new Authority(rol.getNombre()));
        }
        return autoridades;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
