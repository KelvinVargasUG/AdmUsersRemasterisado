CREATE OR REPLACE PACKAGE BODY kjvargas.pkg_usuarios_procedures AS
    PROCEDURE createadminuser (
        p_usuario VARCHAR2, p_password VARCHAR2
    ) AS
        v_id_rol kjvargas.rol.id_rol%TYPE;
        v_id_usuario kjvargas.usuario.id_usuario%TYPE;
    BEGIN
        SELECT
            rol.id_rol
        INTO v_id_rol
        FROM
            kjvargas.rol rol
        WHERE
                id_rol = 1;
        NULL;
    EXCEPTION
        WHEN no_data_found THEN
            INSERT INTO kjvargas.rol (
                fecha_creacion, fecha_modificacion, nombre, descripcion
            ) VALUES (
                                 sysdate, sysdate, 'Rol_Admin', 'Rol de administrador puede crear e eliminar usuarios'
                     ) RETURNING id_rol INTO v_id_rol;
            INSERT INTO kjvargas.rol (
                fecha_creacion, fecha_modificacion, nombre, descripcion
            ) VALUES (
                                 sysdate, sysdate, 'Rol_User', 'Rol de usuario con permisos limitados'
                     );
            INSERT INTO kjvargas.usuario (
                fecha_creacion, fecha_modificacion, email, nombre, apellido, password
            ) VALUES (
                                 sysdate, sysdate, p_usuario, 'admin', 'rol', p_password
                     ) RETURNING id_usuario INTO v_id_usuario;
            INSERT INTO kjvargas.usuario_rol (
                fecha_creacion, fecha_modificacion, id_rol, id_usuario
            ) VALUES (
                                 sysdate, sysdate, v_id_rol, v_id_usuario
                     );
    END createadminuser;

    PROCEDURE find_users_by_id (
        p_id_user IN NUMBER, p_users OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_users FOR SELECT
                             u.id_usuario, u.nombre, u.apellido, u.email, u.estado, rol.nombre AS roles
                         FROM
                             kjvargas.usuario u
                                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                                 INNER JOIN kjvargas.rol         rol ON ur.id_rol = rol.id_rol
                         WHERE
                             u.estado IS NOT NULL
                           AND u.id_usuario = p_id_user
                         ORDER BY
                             u.id_usuario DESC;
    END find_users_by_id;

    PROCEDURE createusuario (
        p_nombre IN VARCHAR2, p_apellido IN VARCHAR2, p_email IN VARCHAR2, p_password IN VARCHAR2, p_users OUT SYS_REFCURSOR
    ) AS
        id_usuario kjvargas.usuario.id_usuario%TYPE;
    BEGIN
        INSERT INTO kjvargas.usuario (
            nombre, apellido, email, password, fecha_creacion, fecha_modificacion
        ) VALUES (
                     p_nombre, p_apellido, p_email, p_password, sysdate, sysdate
                 ) RETURNING id_usuario INTO id_usuario;
        INSERT INTO kjvargas.usuario_rol (
            fecha_creacion, fecha_modificacion, id_rol, id_usuario
        ) VALUES (
                             sysdate, sysdate, 2, id_usuario
                 );
        kjvargas.find_users_by_id(id_usuario, p_users);
    END createusuario;

    PROCEDURE updateusuario (
        p_id_usuario IN NUMBER, p_nombre IN VARCHAR2, p_apellido IN VARCHAR2, p_email IN VARCHAR2, p_estado IN VARCHAR2
    ) AS
    BEGIN
        UPDATE kjvargas.usuario
        SET
            fecha_modificacion = sysdate, nombre = p_nombre, apellido = p_apellido, email = p_email, estado = p_estado
        WHERE
                id_usuario = p_id_usuario;
    END updateusuario;

    PROCEDURE deleteusuario (
        p_id_usuario IN NUMBER
    ) AS
    BEGIN
        UPDATE kjvargas.usuario
        SET
            estado = NULL
        WHERE
                id_usuario = p_id_usuario;
    END deleteusuario;

    PROCEDURE find_all_users (
        p_users OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_users FOR SELECT
                             u.id_usuario, u.nombre, u.apellido, u.email, u.estado, rol.nombre AS roles
                         FROM
                             kjvargas.usuario u
                                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                                 INNER JOIN kjvargas.rol         rol ON ur.id_rol = rol.id_rol
                         WHERE
                             u.estado IS NOT NULL
                         ORDER BY
                             u.id_usuario DESC;
    END find_all_users;

    PROCEDURE find_user_by_email (
        p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_users FOR SELECT
                             u.id_usuario, u.nombre, u.apellido, u.email, u.estado, rol.nombre AS roles
                         FROM
                             kjvargas.usuario u
                                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                                 INNER JOIN kjvargas.rol         rol ON ur.id_rol = rol.id_rol
                         WHERE
                                 u.email = p_email;
    END find_user_by_email;

    PROCEDURE find_user_by_email_load (
        p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR
    ) IS
    BEGIN
        OPEN p_users FOR SELECT
                             u.id_usuario, u.email, u.password
                         FROM
                             kjvargas.usuario u
                                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                                 INNER JOIN kjvargas.rol         rol ON ur.id_rol = rol.id_rol
                         WHERE
                                 u.email = p_email;
    END find_user_by_email_load;
    PROCEDURE habilitar_usuario (
        p_id_user IN NUMBER
    ) AS
    BEGIN
        UPDATE kjvargas.usuario u
        SET
            u.estado = 'A'
        WHERE
                u.id_usuario = p_id_user;
    END habilitar_usuario;
END pkg_usuarios_procedures;