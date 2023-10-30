CREATE OR REPLACE PROCEDURE kjvargas.find_user_by_email(p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_users FOR
SELECT U.id_usuario,
       U.nombre,
       U.apellido,
       U.email,
       U.estado,
       rol.nombre AS ROLES
FROM kjvargas.usuario U
         INNER JOIN kjvargas.usuario_rol ur ON U.id_usuario = ur.id_usuario
         INNER JOIN kjvargas.rol rol ON ur.id_rol = rol.id_rol
WHERE U.email = p_email;
END find_user_by_email;