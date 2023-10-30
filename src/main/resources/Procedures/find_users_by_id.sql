CREATE OR REPLACE PROCEDURE kjvargas.find_users_by_id(p_id_user IN NUMBER, p_users OUT SYS_REFCURSOR)
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
WHERE U.estado IS NOT NULL
  AND U.id_usuario = p_id_user
ORDER BY U.id_usuario DESC;
END find_users_by_id;