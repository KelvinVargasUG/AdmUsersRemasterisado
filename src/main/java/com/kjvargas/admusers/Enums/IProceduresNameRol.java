package com.kjvargas.admusers.Enums;

import org.springframework.beans.factory.annotation.Value;

public interface IProceduresNameRol {

    String PACKAGE_ROL = "PKG_ROL_PROCEDURES.";

    String FIND_ALL_ROLES =  PACKAGE_ROL + "find_all_rol";

    String FIND_ALL_ROLES_NO_ASIGNADO_USER =  PACKAGE_ROL + "roles_no_asignado_user";

    String FIND_ALL_ROLES_BY_ID =  PACKAGE_ROL + "find_all_rol_by_id";
}
