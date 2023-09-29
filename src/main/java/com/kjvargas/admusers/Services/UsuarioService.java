package com.kjvargas.admusers.Services;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Repositories.UsuarioRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepositoy usuarioRepositoy;

    public List<Usuario> findAll() {
        return usuarioRepositoy.findAll();
    }

    public void insert(Usuario usuario) {
        usuarioRepositoy.insert(usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getPassword());
    }
}
