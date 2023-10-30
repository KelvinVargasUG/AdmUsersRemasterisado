CREATE OR REPLACE PROCEDURE kjvargas.find_all_rol(p_rol OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_rol FOR
SELECT rol.id_rol, rol.nombre, rol.descripcion
FROM kjvargas.rol rol
WHERE rol.estado IS NOT NULL;
END find_all_rol;