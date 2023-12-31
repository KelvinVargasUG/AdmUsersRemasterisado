CREATE OR REPLACE PACKAGE PKG_ROL_PROCEDURES AS

	PROCEDURE find_all_rol(p_rol OUT SYS_REFCURSOR);

	PROCEDURE roles_no_asignado_user(p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR);

	PROCEDURE find_all_rol_by_id(p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR);

END PKG_ROL_PROCEDURES;