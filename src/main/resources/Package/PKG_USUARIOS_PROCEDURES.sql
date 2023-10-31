create or replace PACKAGE kjvargas.PKG_USUARIOS_PROCEDURES AS
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
END PKG_USUARIOS_PROCEDURES;

