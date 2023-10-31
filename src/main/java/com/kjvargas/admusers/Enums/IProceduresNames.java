package com.kjvargas.admusers.Enums;

public interface IProceduresNames {

    static final String SCHEMA = "kjvargas.";

    static final String PACKAGE_USUARIO = "PKG_USUARIOS_PROCEDURES.";

    static final String PACKAGE_ROL = "PKG_ROL_PROCEDURES.";

    String FIND_ALL_USERS = SCHEMA + PACKAGE_USUARIO + "find_all_users";

    String CREATE_ADMIN_USER = SCHEMA + PACKAGE_USUARIO + "createAdminUser";

    String DELETE_USUARIO = SCHEMA + PACKAGE_USUARIO + "deleteUsuario";

    String HABILITAR_USUARIO = SCHEMA + PACKAGE_USUARIO + "habilitar_usuario";

    String CREATE_USUARIO = SCHEMA + PACKAGE_USUARIO + "createUsuario";

    String UPDATE_USUARIO = SCHEMA + PACKAGE_USUARIO + "updateUsuario";

    String UPDATE_ROL_USER = SCHEMA + PACKAGE_USUARIO + "update_rol_user";

    String FIND_USER_BY_EMAIL_LOAD = SCHEMA + PACKAGE_USUARIO + "find_user_by_email_load";

    String FIND_USERS_BY_ID = SCHEMA + PACKAGE_USUARIO + "find_users_by_id";

    String FIND_USER_BY_EMAIL = SCHEMA + PACKAGE_USUARIO + "find_user_by_email";


    String FIND_ALL_ROLES = SCHEMA + PACKAGE_ROL + "find_all_rol";

    String FIND_ALL_ROLES_NO_ASIGNADO_USER = SCHEMA + PACKAGE_ROL + "roles_no_asignado_user";

    String FIND_ALL_ROLES_BY_ID = SCHEMA + PACKAGE_ROL + "find_all_rol_by_id";

}
