--alter session set "_ORACLE_SCRIPT"= true;

--Crea usuario
--CREATE USER kjvargas IDENTIFIED BY 12345;

--Privilegio de admin
--GRANT DBA TO kjvargas;

--privilegios de coneccion
--GRANT CONNECT, RESOURCE TO kjvargas;

--Create usuario Admin--------------------------------------------------------------------
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



--find usuario by id--------------------------------------------------------------------
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


--Create usuario User--------------------------------------------------------------------
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



--Update usuario----------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.updateusuario(p_id_usuario IN NUMBER,
                                                   p_nombre IN VARCHAR2,
                                                   p_apellido IN VARCHAR2,
                                                   p_email IN VARCHAR2,
                                                 --  p_password in varchar2,
                                                   p_estado IN VARCHAR2,
                                                   p_users OUT SYS_REFCURSOR)
AS
BEGIN
UPDATE kjvargas.usuario
SET fecha_modificacion = sysdate,
    nombre             = p_nombre,
    apellido           = p_apellido,
    email              = p_email,
    -- password           = p_password,
    estado             = p_estado
WHERE id_usuario = p_id_usuario;

kjvargas.find_users_by_id(p_id_usuario, p_users);
END updateusuario;



--Delete usuario----------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.deleteusuario(p_id_usuario IN NUMBER)
AS
BEGIN
UPDATE kjvargas.usuario
SET estado = NULL
WHERE id_usuario = p_id_usuario;
END deleteusuario;



--find all usuarios--------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.find_all_users(p_users OUT SYS_REFCURSOR)
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
ORDER BY U.id_usuario DESC;
END find_all_users;


---Comprobar existencia del email-----------------------------------------------------------------------
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

--loadUser------------------------------------------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.find_user_by_email_load(p_email IN VARCHAR2, p_users OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_users FOR
SELECT U.email,
       U.PASSWORD
FROM kjvargas.usuario U
         INNER JOIN kjvargas.usuario_rol ur ON U.id_usuario = ur.id_usuario
         INNER JOIN kjvargas.rol rol ON ur.id_rol = rol.id_rol
WHERE U.email = p_email;
END find_user_by_email_load;


--habilitar usuario--------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.habilitar_usuario(p_id_user IN NUMBER)
AS
BEGIN
UPDATE kjvargas.usuario U SET U.estado = 'A' WHERE U.id_usuario = p_id_user;
END habilitar_usuario;



--cambar rol usuario--------------------------------------------------------------------
create or replace procedure update_rol_user(p_id_rol in number,p_id_user in number) as
begin
update KJVARGAS.USUARIO_ROL u set u.id_rol = p_id_rol where u.ID_USUARIO = p_id_user;
end;



--find all rol--------------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.find_all_rol(p_rol OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_rol FOR
SELECT rol.id_rol, rol.nombre, rol.descripcion
FROM kjvargas.rol rol
WHERE rol.estado IS NOT NULL;
END find_all_rol;



--findByUserUnassignedRoles------------------------------------------------------------
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



--findByUserAssignedRoles------------------------------------------------------------
CREATE OR REPLACE PROCEDURE kjvargas.find_all_rol_by_id(p_id_user IN NUMBER, p_rol OUT SYS_REFCURSOR)
    IS
BEGIN
OPEN p_rol FOR
SELECT ur.id_rol, R.nombre
FROM kjvargas.usuario_rol ur
         INNER JOIN kjvargas.rol R ON R.id_rol = ur.id_rol
WHERE ur.estado IS NOT NULL
  AND ur.id_usuario = p_id_user;
END find_all_rol_by_id;