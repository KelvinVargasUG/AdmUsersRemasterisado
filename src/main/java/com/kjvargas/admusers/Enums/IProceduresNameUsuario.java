package com.kjvargas.admusers.Enums;

import org.springframework.beans.factory.annotation.Value;

public interface IProceduresNameUsuario {

    String PACKAGE_USUARIO = "PKG_USUARIOS_PROCEDURES.";


    String FIND_ALL_USERS =  PACKAGE_USUARIO + "find_all_users";

    String CREATE_ADMIN_USER =  PACKAGE_USUARIO + "createAdminUser";

    String DELETE_USUARIO =  PACKAGE_USUARIO + "deleteUsuario";

    String HABILITAR_USUARIO =  PACKAGE_USUARIO + "habilitar_usuario";

    String CREATE_USUARIO =  PACKAGE_USUARIO + "createUsuario";

    String UPDATE_USUARIO =  PACKAGE_USUARIO + "updateUsuario";

    String UPDATE_ROL_USER =  PACKAGE_USUARIO + "update_rol_user";

    String FIND_USER_BY_EMAIL_LOAD =  PACKAGE_USUARIO + "find_user_by_email_load";

    String FIND_USERS_BY_ID =  PACKAGE_USUARIO + "find_users_by_id";

    String FIND_USER_BY_EMAIL =  PACKAGE_USUARIO + "find_user_by_email";

}
