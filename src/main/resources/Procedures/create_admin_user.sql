CREATE OR REPLACE PROCEDURE kjvargas.createadminuser(p_usuario varchar2, p_password VARCHAR2) AS
    v_id_rol kjvargas.rol.ID_ROL%TYPE;
    v_id_usuario kjvargas.usuario.ID_USUARIO%type;
BEGIN
SELECT rol.id_rol INTO v_id_rol FROM kjvargas.rol rol WHERE id_rol = 1;
NULL;
EXCEPTION
    WHEN no_data_found THEN
        INSERT INTO kjvargas.rol
        (fecha_creacion, fecha_modificacion, nombre, descripcion)
        VALUES (sysdate, sysdate, 'Rol_Admin', 'Rol de administrador puede crear e eliminar usuarios')
        RETURNING ID_ROL INTO v_id_rol;

INSERT INTO kjvargas.ROL
(fecha_creacion, fecha_modificacion, nombre, descripcion)
VALUES (sysdate, sysdate, 'Rol_User', 'Rol de usuario con permisos limitados');

INSERT INTO kjvargas.usuario
(fecha_creacion, fecha_modificacion, email, nombre, apellido, PASSWORD)
VALUES (sysdate, sysdate, p_usuario, 'admin', 'rol', p_password)
    RETURNING ID_USUARIO INTO v_id_usuario;

INSERT INTO kjvargas.usuario_rol
(fecha_creacion, fecha_modificacion, id_rol, id_usuario)
VALUES (sysdate, sysdate, v_id_rol, v_id_usuario);
END createadminuser;