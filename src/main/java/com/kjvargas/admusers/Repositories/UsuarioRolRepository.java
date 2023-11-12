package com.kjvargas.admusers.Repositories;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Enums.IProceduresNameRol;
import com.kjvargas.admusers.Enums.IProceduresNameUsuario;
import com.kjvargas.admusers.SecurityJwt.Entitys.UsuarioSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsuarioRolRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Rol> findByUserUnassignedRoles(Long id) {
        Usuario comprobarId = findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameRol.FIND_ALL_ROLES_NO_ASIGNADO_USER)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter(1, id);
        storedProcedure.execute();

        List<Rol> roles = new ArrayList<>();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            Rol rol = new Rol();
            rol.setId(((Number) row[0]).longValue());
            rol.setNombre((String) row[1]);
            roles.add(rol);
        }
        return roles;
    }

    public List<Rol> findRolByUserId(Long id) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameRol.FIND_ALL_ROLES_BY_ID)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter(1, id);
        storedProcedure.execute();

        List<Rol> rolesUsuario = new ArrayList<>();
        Rol rol = new Rol();
        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            rol.setId(((Number) row[0]).longValue());
            rol.setNombre((String) row[1]);
            rol.setDescripcion((String) row[2]);
            rolesUsuario.add(rol);
        }
        if(rolesUsuario.isEmpty()){
            throw new RuntimeException("El usuario no tiene roles asignados");
        }
        return rolesUsuario;
    }

    public Usuario findByIdUser(Long id) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.FIND_USERS_BY_ID)
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

        if (usuario.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }

        List<Rol> roles = findRolByUserId(id);
        usuario.setRoles(roles);
        return usuario;
    }

    public Usuario updateRolUser(Long id_rol, Long id_user) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.UPDATE_ROL_USER)
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
        storedProcedure.setParameter(1, id_rol);
        storedProcedure.setParameter(2, id_user);
        storedProcedure.execute();

        Usuario usuario = findByIdUser(id_user);

        return usuario;
    }

    public Usuario findByIdEmail(String email) {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.FIND_USER_BY_EMAIL)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter(1, email);
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

        List<Rol> roles = findRolByUserId(usuario.getId());
        usuario.setRoles(roles);
        return usuario;
    };

    public UsuarioSecurity findByIdEmailLoad(String email) {
        Long idUser = 0L;
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("El email no puede ser nulo o vacio");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNameUsuario.FIND_USER_BY_EMAIL_LOAD)
                .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, void.class, ParameterMode.REF_CURSOR);
        storedProcedure.setParameter(1, email);
        storedProcedure.execute();

        UsuarioSecurity usuario = new UsuarioSecurity();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            idUser = (((Number) row[0]).longValue());
            usuario.setEmail((String) row[1]);
            usuario.setPassword((String) row[2]);
        }

        List<Rol> roles = findRolByUserId(idUser);
        usuario.setRoles(roles);

        return usuario;
    };

}
