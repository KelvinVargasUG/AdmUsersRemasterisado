package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepositoy;

    public Usuario createUser(Usuario usuario) {
        return null;
    }

    public Page<Usuario> findAllUser() {
        return null;
    }

    public Usuario findByIdUser(Long id) {
        return null;
    }

    public Usuario updateUser(Usuario usuario, Long id) {
        return null;
    }

    public String deleteUser(Long id) {
        return null;
    }
}
