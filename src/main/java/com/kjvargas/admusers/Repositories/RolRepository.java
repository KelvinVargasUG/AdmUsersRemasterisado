package com.kjvargas.admusers.Repositories;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Enums.IProceduresNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RolRepository {

    @Autowired
    private EntityManager entityManager;

    public List<Rol> findAllRoles() {
        StoredProcedureQuery storedProcedure = entityManager
                .createStoredProcedureQuery(IProceduresNames.FIND_ALL_ROLES)
                .registerStoredProcedureParameter(1, void.class, ParameterMode.REF_CURSOR);

        storedProcedure.execute();

        List<Rol> roles = new ArrayList<>();

        List<Object[]> resultList = storedProcedure.getResultList();
        for (Object[] row : resultList) {
            Rol rol = new Rol();
            rol.setId(((Number) row[0]).longValue());
            rol.setNombre((String) row[1]);
            rol.setDescripcion((String) row[2]);
            roles.add(rol);
        }
        return roles;
    }

}
