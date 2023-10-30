CREATE OR REPLACE PROCEDURE kjvargas.find_all_rol_by_id(p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_rol FOR
SELECT ur.id_rol, R.nombre, R.descripcion
FROM kjvargas.usuario_rol ur
         INNER JOIN kjvargas.rol R ON R.id_rol = ur.id_rol
WHERE ur.estado IS NOT NULL
  AND ur.id_usuario = p_id_user;
END find_all_rol_by_id;