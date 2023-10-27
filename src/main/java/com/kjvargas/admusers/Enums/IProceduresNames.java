package com.kjvargas.admusers.Enums;

public interface IProceduresNames {

    static final String SCHEMA = "kjvargas.";

    String FIND_ALL_USERS = SCHEMA+"find_all_users";

    String CREATE_ADMIN_USER = SCHEMA+"createAdminUser";

    String DELETE_USUARIO = SCHEMA+"deleteUsuario";

    String HABILITAR_USUARIO = SCHEMA+"habilitar_usuario";

    String CREATE_USUARIO = SCHEMA+"createUsuario";

    String UPDATE_USUARIO = SCHEMA+"updateUsuario";

    String FIND_ALL_ROLES = SCHEMA+"find_all_rol";

    String FIND_ALL_ROLES_NO_ASIGNADO_USER = SCHEMA+"roles_no_asignado_user";

    String FIND_ALL_ROLES_BY_ID = SCHEMA+"find_all_rol_by_id";

    String FIND_USERS_BY_ID = SCHEMA+"find_users_by_id";

    String UPDATE_ROL_USER = SCHEMA+"update_rol_user";

    String FIND_USER_BY_EMAIL = SCHEMA+"find_user_by_email";

    String FIND_USER_BY_EMAIL_LOAD = SCHEMA+"find_user_by_email_load";
}
