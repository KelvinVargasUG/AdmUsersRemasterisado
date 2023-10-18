package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UsuarioRolService usuarioRolService;

    @PostConstruct
    private void createUserAdmin() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.createAdminUser");
        storedProcedure.execute();
    }

    public Usuario createUser(Usuario usuario) {
        Usuario emailExist = usuarioRolService.findByIdEmail(usuario.getEmail());
        if (emailExist.getEmail().equals(usuario.getEmail())) {
            if (emailExist.getEstado() == null) {
                habilitarUsuario(emailExist.getId());
                throw new RuntimeException("El email ya existe, se habilito el usuario");
            } else {
                throw new RuntimeException("El email ya existe");
            }
        }
        if (usuario.getEstado() == null) {
            StoredProcedureQuery storedProcedure = entityManager
                    .createStoredProcedureQuery("kjvargas.createUsuario")
                    .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, void.class, ParameterMode.REF_CURSOR);

            storedProcedure.setParameter(1, usuario.getNombre());
            storedProcedure.setParameter(2, usuario.getApellido());
            storedProcedure.setParameter(3, usuario.getEmail());
            storedProcedure.setParameter(4, usuario.getPassword());

            storedProcedure.execute();

            Usuario usuarioResult = new Usuario();

            List<Object[]> resultList = storedProcedure.getResultList();
            for (Object[] row : resultList) {
                usuarioResult.setId(((Number) row[0]).longValue());
                usuarioResult.setNombre((String) row[1]);
                usuarioResult.setApellido((String) row[2]);
                usuarioResult.setEmail((String) row[3]);
                usuarioResult.setEstado((String) row[4]);
            }

            return usuarioResult;
        } else {
            throw new RuntimeException("No se puede crear un usuario con estado");
        }
    }

    public List<Usuario> findAllUser() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.find_all_users")
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.execute();

        List<Usuario> usuarios = new ArrayList<>();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            Usuario usuario = new Usuario();
            usuario.setId(((Number) row[0]).longValue());
            usuario.setNombre((String) row[1]);
            usuario.setApellido((String) row[2]);
            usuario.setEmail((String) row[3]);
            usuario.setEstado((String) row[4]);

            usuarios.add(usuario);
        }
        if (usuarios.isEmpty()) {
            throw new RuntimeException("No hay usuarios");
        }
        return usuarios;
    }

    public Usuario updateUser(Usuario usuario, Long id) {
        Usuario comprobarId = usuarioRolService.findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.updateUsuario")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, void.class, ParameterMode.REF_CURSOR);

        storedProcedure.setParameter(1, id);
        storedProcedure.setParameter(2, usuario.getNombre());
        storedProcedure.setParameter(3, usuario.getApellido());
        storedProcedure.setParameter(4, usuario.getEmail());
        storedProcedure.setParameter(5, usuario.getPassword());
        storedProcedure.setParameter(6, usuario.getEstado());

        storedProcedure.execute();

        Usuario usuarioResult = new Usuario();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            usuarioResult.setId(((Number) row[0]).longValue());
            usuarioResult.setNombre((String) row[1]);
            usuarioResult.setApellido((String) row[2]);
            usuarioResult.setEmail((String) row[3]);
            usuarioResult.setEstado((String) row[4]);
        }

        return usuarioResult;
    }

    public String deleteUser(Long id) {
        Usuario comprobarId = usuarioRolService.findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.deleteUsuario")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id);

        storedProcedure.execute();
        return "Usuario eliminado";
    }

    public void habilitarUsuario(Long id_user) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.habilitar_usuario")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id_user);
        storedProcedure.execute();
    }

}
