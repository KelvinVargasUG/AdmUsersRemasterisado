CREATE OR REPLACE PROCEDURE kjvargas.roles_no_asignado_user(p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_rol FOR
SELECT R.id_rol, R.nombre
FROM (SELECT rol.id_rol
      FROM kjvargas.rol rol
      WHERE rol.estado IS NOT NULL
          MINUS
      SELECT ur.id_rol
      FROM kjvargas.usuario_rol ur
      WHERE ur.estado IS NOT NULL
        AND ur.id_usuario = p_id_user) tx
         INNER JOIN kjvargas.rol R ON R.id_rol = tx.id_rol;
END roles_no_asignado_user;