package com.kjvargas.admusers.Enums;

public interface IProceduresNames {
    String FIND_ALL_USERS = "kjvargas.find_all_users";

    String CREATE_ADMIN_USER = "kjvargas.createAdminUser";

    String DELETE_USUARIO = "kjvargas.deleteUsuario";

    String HABILITAR_USUARIO = "kjvargas.habilitar_usuario";

    String CREATE_USUARIO = "kjvargas.createUsuario";

    String UPDATE_USUARIO = "kjvargas.updateUsuario";

    String FIND_ALL_ROLES = "kjvargas.find_all_rol";

    String FIND_ALL_ROLES_NO_ASIGNADO_USER = "kjvargas.roles_no_asignado_user";

    String FIND_ALL_ROLES_BY_ID = "kjvargas.find_all_rol_by_id";

    String FIND_USERS_BY_ID = "kjvargas.find_users_by_id";

    String UPDATE_ROL_USER = "kjvargas.update_rol_user";

    String FIND_USER_BY_EMAIL = "kjvargas.find_user_by_email";

    String FIND_USER_BY_EMAIL_LOAD = "kjvargas.find_user_by_email_load";
}
