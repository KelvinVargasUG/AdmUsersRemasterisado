alter session set "_ORACLE_SCRIPT"=true;

--Crea usuario
CREATE USER kjvargas IDENTIFIED BY 12345;

--Privilegio de admin
GRANT DBA TO kjvargas;

--privilegios de coneccion
GRANT CONNECT, RESOURCE TO kjvargas;

--Create usuario Admin--------------------------------------------------------------------
create or replace PROCEDURE kjvargas.createAdminUser as
    id_rol kjvargas.rol.id_rol%type;
begin
    select rol.id_rol into id_rol from kjvargas.rol rol where id_rol = 1;
    null;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        insert into kjvargas.rol
            (FECHA_CREACION, FECHA_MODIFICACION, nombre)
        values
            (sysdate, sysdate, 'Rol_Admin');

        insert into kjvargas.rol
        (FECHA_CREACION, FECHA_MODIFICACION, nombre)
        values
            (sysdate, sysdate, 'Rol_User');

        insert into kjvargas.usuario
        (FECHA_CREACION, FECHA_MODIFICACION, EMAIL, NOMBRE, apellido, PASSWORD)
        values
            (sysdate, sysdate, 'admin@hotmail.com', 'admin','rol','12345');

        insert into kjvargas.usuario_rol
        (FECHA_CREACION, FECHA_MODIFICACION,ID_ROL,ID_USUARIO)
        values
            (sysdate, sysdate,1,1);
end;
--------------------------------------------------------------------------------------------

--Create usuario User--------------------------------------------------------------------
create or replace PROCEDURE kjvargas.createUsuario
(p_nombre   in varchar2,
 p_apellido in varchar2,
 p_email    in varchar2,
 p_password in varchar2,
 p_users out SYS_REFCURSOR)
as
    id_usuario kjvargas.usuario.ID_USUARIO%type;
begin
    insert into kjvargas.USUARIO (NOMBRE, APELLIDO, EMAIL, PASSWORD, FECHA_CREACION, FECHA_MODIFICACION)
    values (p_nombre, p_apellido, p_email, p_password,sysdate,sysdate)
    RETURNING ID_USUARIO INTO id_usuario;

    insert into kjvargas.usuario_rol
    (FECHA_CREACION, FECHA_MODIFICACION,ID_ROL,ID_USUARIO)
    values
        (sysdate, sysdate,2,id_usuario);

    kjvargas.find_users_by_id(id_usuario, p_users);

end;

--------------------------------------------------------------------------------------

--Update usuario----------------------------------------------------------------------
create or replace PROCEDURE kjvargas.updateUsuario
(p_id_usuario in NUMBER,
 p_nombre in varchar2,
 p_apellido in varchar2,
 p_email in varchar2,
 p_password in varchar2,
 p_estado in varchar2,
 p_users out SYS_REFCURSOR)

as
begin
    UPDATE kjvargas.usuario
    set
        fecha_modificacion = SYSDATE,
        nombre             = p_nombre,
        apellido           = p_apellido,
        email              = p_email,
        password           = p_password,
        estado             = p_estado
    where id_usuario       = p_id_usuario;

    kjvargas.find_users_by_id(p_id_usuario, p_users);
end;
--------------------------------------------------------------------------------------

--Delete usuario----------------------------------------------------------------------
create or replace PROCEDURE kjvargas.deleteUsuario
(p_id_usuario in NUMBER)
as
begin
    UPDATE kjvargas.usuario
    set
        estado           = null
    where id_usuario   = p_id_usuario;
end;
--------------------------------------------------------------------------------------

--find all usuarios--------------------------------------------------------------------
create or replace PROCEDURE kjvargas.find_all_users (p_users out SYS_REFCURSOR)
    IS
BEGIN
    OPEN p_users FOR
        SELECT
            u.id_usuario, u.nombre,
            u.apellido, u.email,
            u.estado, rol.nombre AS roles
        FROM kjvargas.usuario u
                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                 INNER JOIN kjvargas.rol rol ON ur.id_rol = rol.id_rol
        WHERE u.estado IS NOT NULL
        ORDER BY u.id_usuario DESC;
END;
--------------------------------------------------------------------------------------

--find usuario by id--------------------------------------------------------------------
create or replace PROCEDURE kjvargas.find_users_by_id (p_id_user in number, p_users out SYS_REFCURSOR)
    IS
BEGIN
    OPEN p_users FOR
        SELECT
            u.id_usuario, u.nombre,
            u.apellido, u.email,
            u.estado, rol.nombre AS roles
        FROM kjvargas.usuario u
                 INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
                 INNER JOIN kjvargas.rol rol ON ur.id_rol = rol.id_rol
        WHERE u.estado IS NOT NULL
          and u.id_usuario = p_id_user
        ORDER BY u.id_usuario DESC;
END;
--------------------------------------------------------------------------------------

--find all rol--------------------------------------------------------------------
create or replace PROCEDURE kjvargas.find_all_rol(p_rol out SYS_REFCURSOR)
    IS
BEGIN
    OPEN p_rol FOR
        SELECT rol.id_rol, rol.nombre
        FROM kjvargas.rol rol
        WHERE rol.estado IS NOT NULL;
END;
--------------------------------------------------------------------------------------

--findByUserUnassignedRoles------------------------------------------------------------
create or replace PROCEDURE kjvargas.roles_no_asignado_user(p_id_user in number, p_rol out SYS_REFCURSOR)
    IS
BEGIN
    OPEN p_rol FOR
        select r.id_rol, r.nombre
        from(
                SELECT rol.id_rol
                FROM kjvargas.rol rol
                WHERE rol.estado IS NOT NULL
                minus
                select ur.id_rol
                from kjvargas.usuario_rol ur
                WHERE ur.estado IS NOT NULL
                  and ur.id_usuario = p_id_user
            ) tx
                inner join kjvargas.rol r on r.id_rol = tx.id_rol;
END;
--------------------------------------------------------------------------------------