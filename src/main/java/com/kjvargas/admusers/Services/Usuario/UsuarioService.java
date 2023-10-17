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

    @PostConstruct
    private void createUserAdmin() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.createAdminUser");
        storedProcedure.execute();
    }


    public Usuario createUser(Usuario usuario) {
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

        return usuarios;
    }

    public Usuario findByIdUser(Long id) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.find_users_by_id")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter(1, id);
        storedProcedure.execute();

        Usuario usuario = new Usuario();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            usuario.setId(((Number) row[0]).longValue());
            usuario.setNombre((String) row[1]);
            usuario.setApellido((String) row[2]);
            usuario.setEmail((String) row[3]);
            usuario.setEstado((String) row[4]);
        }

        StoredProcedureQuery storedProcedureRol = entityManager
                .createStoredProcedureQuery("kjvargas.find_all_rol_by_id")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedureRol.setParameter(1, id);
        storedProcedureRol.execute();


        List<Rol> rolesUsuario = new ArrayList<>();
        Rol rol = new Rol();
        List<Object[]> resultListRol = storedProcedureRol.getResultList();
        for (Object[] row : resultListRol) {
            rol.setId(((Number) row[0]).longValue());
            rol.setNombre((String) row[1]);
            System.out.println(row[1]);
            rolesUsuario.add(rol);
        }

        usuario.setRoles(rolesUsuario);

        return usuario;
    }

    public Usuario updateUser(Usuario usuario, Long id) {
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
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.deleteUsuario")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id);

        storedProcedure.execute();
        return "Usuario eliminado";
    }
}
