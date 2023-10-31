CREATE OR REPLACE PACKAGE BODY pkg_rol_procedures AS
	PROCEDURE find_all_rol (
		p_rol OUT SYS_REFCURSOR
	) IS
BEGIN
OPEN p_rol FOR SELECT
			                              rol.id_rol, rol.nombre, rol.descripcion
		                              FROM
			                              kjvargas.rol rol
		               WHERE
			               rol.estado IS NOT NULL;
END find_all_rol;
	PROCEDURE roles_no_asignado_user (
		p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR
	) IS
BEGIN
OPEN p_rol FOR SELECT
			               r.id_rol, r.nombre
		               FROM
			                    (
				               SELECT
					               rol.id_rol
				               FROM
					               kjvargas.rol rol
				               WHERE
					               rol.estado IS NOT NULL
				               MINUS
				               SELECT
					               ur.id_rol
				               FROM
					               kjvargas.usuario_rol ur
				               WHERE
					               ur.estado IS NOT NULL
					               AND ur.id_usuario = p_id_user
			               ) tx
			               INNER JOIN kjvargas.rol r ON r.id_rol = tx.id_rol;
END roles_no_asignado_user;
	PROCEDURE find_all_rol_by_id (
		p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR
	) IS
BEGIN
OPEN p_rol FOR SELECT
			                              ur.id_rol, r.nombre, r.descripcion
		                              FROM
			                                   kjvargas.usuario_rol ur
			                              INNER JOIN kjvargas.rol r ON r.id_rol = ur.id_rol
		               WHERE
			               ur.estado IS NOT NULL
			               AND ur.id_usuario = p_id_user;
END find_all_rol_by_id;
END pkg_rol_procedures;