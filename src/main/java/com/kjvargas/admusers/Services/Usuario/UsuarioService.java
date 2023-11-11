package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Repositories.UsuarioRepository;
import com.kjvargas.admusers.Repositories.UsuarioRolRepository;
import com.kjvargas.admusers.SecurityJwt.Entitys.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    public void createUserAdmin(String emailAdmin, String passwordAdmin) {
        usuarioRepository.createUserAdmin(emailAdmin, passwordAdmin);
    }

    public ResponseEntity<Usuario> createUser(Usuario usuario) {
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacio");
        }
        try {
            Usuario usuarioResult = usuarioRepository.createUser(usuario);
            return ResponseEntity.ok(usuarioResult);
        } catch (Exception e) {
            Usuario emailExist = usuarioRolRepository.findByIdEmail(usuario.getEmail());
            if (emailExist != null) {
                if (emailExist.getEstado() == null) {
                    this.usuarioRepository.habilitarUsuario(emailExist.getId());
                    throw new RuntimeException("El email ya existe, se habilitó el usuario");
                }
                throw new RuntimeException("El correo electrónico ya existe");
            }
            return null;
        }
    }

    public List<Usuario> findAllUser() {
        List<Usuario> usuarios = usuarioRepository.findAllUser();

        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios");
        }
        return usuarios;
    }

    public Usuario updateUser(Usuario usuario, Long id) {
        Usuario usuarioByid = usuarioRolRepository.findByIdUser(id);
        if (usuarioByid.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        return this.usuarioRepository.updateUser(usuario, id);
    }

    public ResponseEntity<?> deleteUser(long id) {
        this.usuarioRepository.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}
