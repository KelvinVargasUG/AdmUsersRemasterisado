CREATE OR REPLACE PROCEDURE kjvargas.createusuario(p_nombre IN VARCHAR2,
                                                   p_apellido IN VARCHAR2,
                                                   p_email IN VARCHAR2,
                                                   p_password IN VARCHAR2,
                                                   p_users OUT SYS_REFCURSOR)
AS
    id_usuario kjvargas.usuario.id_usuario%TYPE;
BEGIN
INSERT INTO kjvargas.usuario (nombre, apellido, email, PASSWORD, fecha_creacion, fecha_modificacion)
VALUES (p_nombre, p_apellido, p_email, p_password, sysdate, sysdate)
    RETURNING id_usuario INTO id_usuario;

INSERT INTO kjvargas.usuario_rol
(fecha_creacion, fecha_modificacion, id_rol, id_usuario)
VALUES (sysdate, sysdate, 2, id_usuario);

kjvargas.find_users_by_id(id_usuario, p_users);

END createusuario;