package com.kjvargas.admusers.Repositories;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Enums.IProceduresNameUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRepository {

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Usuario> findAllUser() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.FIND_ALL_USERS)
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

    public void createUserAdmin(String emailAdmin, String passwordAdmin) {
        String passwordEncrypt = this.bCryptPasswordEncoder.encode(passwordAdmin);

        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.CREATE_ADMIN_USER)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN);

        storedProcedure.setParameter(1, emailAdmin);
        storedProcedure.setParameter(2, passwordEncrypt);

        storedProcedure.execute();
    }

    public void deleteUser(long id) {
        Usuario comprobarId = usuarioRolRepository.findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.DELETE_USUARIO)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id);

        storedProcedure.execute();
    }

    public void habilitarUsuario(Long id_user) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.HABILITAR_USUARIO)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id_user);
        storedProcedure.execute();
    }

    public Usuario createUser(Usuario usuario) {
        String passwordEncrypt = this.bCryptPasswordEncoder.encode(usuario.getPassword());

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(IProceduresNameUsuario.CREATE_USUARIO)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, void.class, ParameterMode.REF_CURSOR);

        storedProcedure.setParameter(1, usuario.getNombre());
        storedProcedure.setParameter(2, usuario.getApellido());
        storedProcedure.setParameter(3, usuario.getEmail());
        storedProcedure.setParameter(4, passwordEncrypt);

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

    public Usuario updateUser(Usuario usuario, Long id) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.UPDATE_USUARIO)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN);

        storedProcedure.setParameter(1, id);
        storedProcedure.setParameter(2, usuario.getNombre());
        storedProcedure.setParameter(3, usuario.getApellido());
        storedProcedure.setParameter(4, usuario.getEmail());
        storedProcedure.setParameter(5, usuario.getEstado());

        storedProcedure.execute();

        Usuario usuarioResult = this.usuarioRolRepository.findByIdUser(id);

        return usuarioResult;
    }
}
