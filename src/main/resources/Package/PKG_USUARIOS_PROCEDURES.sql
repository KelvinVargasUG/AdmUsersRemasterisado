create or replace PACKAGE PKG_USUARIOS_PROCEDURES AS
    PROCEDURE createadminuser(p_usuario varchar2, p_password VARCHAR2);
    PROCEDURE find_users_by_id(p_id_user IN NUMBER, p_users OUT SYS_REFCURSOR);
    PROCEDURE createusuario(p_nombre IN VARCHAR2,
                                                       p_apellido IN VARCHAR2,
                                                       p_email IN VARCHAR2,
                                                       p_password IN VARCHAR2,
                                                       p_users OUT SYS_REFCURSOR);
    PROCEDURE updateusuario(p_id_usuario IN NUMBER,
                                                       p_nombre IN VARCHAR2,
                                                       p_apellido IN VARCHAR2,
                                                       p_email IN VARCHAR2,
                                                       p_estado IN VARCHAR2);
     PROCEDURE deleteusuario(p_id_usuario IN NUMBER);
     PROCEDURE find_all_users(p_users OUT SYS_REFCURSOR);
     PROCEDURE find_user_by_email(p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR);
     PROCEDURE find_user_by_email_load(p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR);
     PROCEDURE habilitar_usuario(p_id_user IN NUMBER);
     procedure update_rol_user(p_id_rol in number,p_id_user in number);
END PKG_USUARIOS_PROCEDURES;

