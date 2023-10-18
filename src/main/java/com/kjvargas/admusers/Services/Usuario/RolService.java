package com.kjvargas.admusers.Services.Usuario;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;


@Service
public class RolService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UsuarioService usuarioService;

    public List<Rol> findAllRoles() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.find_all_rol")
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

        storedProcedure.execute();

        List<Rol> roles = new ArrayList<>();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            Rol rol = new Rol();
            rol.setId(((Number) row[0]).longValue());
            rol.setNombre((String) row[1]);
            roles.add(rol);
        }
        if (roles.isEmpty()) {
            throw new RuntimeException("No hay roles");
        }
        return roles;
    }

    public List<Rol> findByUserUnassignedRoles(Long id) {
        Usuario comprobarId = usuarioService.findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.roles_no_asignado_user")
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
        Usuario comprobarId = usuarioService.findByIdUser(id);
        if (comprobarId.getId() == null) {
            throw new RuntimeException("El usuario no existe");
        }
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery("kjvargas.find_all_rol_by_id")
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
            System.out.println(row[1]);
            rolesUsuario.add(rol);
        }

        return rolesUsuario;
    }
}
