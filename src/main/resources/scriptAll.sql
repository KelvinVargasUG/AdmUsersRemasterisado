create or replace procedure kjvargas.usuariosProcedure
(p_accion   in varchar2,

 p_id_usuario in NUMBER,
 p_nombre   in varchar2,
 p_apellido in varchar2,
 p_email    in varchar2,
 p_password in varchar2,
 p_estado in varchar2,

 p_status   out varchar2,
 p_message out varchar2,
 p_result out SYS_REFCURSOR
)
as
    id_usuario kjvargas.usuario.ID_USUARIO%type;
begin
case p_accion
        when 'createAdmin' then
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

p_status := 'OK';
            p_message := 'Usuario creado correctamente';


when 'createUser' then
            insert into kjvargas.USUARIO (NOMBRE, APELLIDO, EMAIL, PASSWORD, FECHA_CREACION, FECHA_MODIFICACION)
            values (p_nombre, p_apellido, p_email, p_password,sysdate,sysdate)
            RETURNING ID_USUARIO INTO id_usuario;

insert into kjvargas.usuario_rol
(FECHA_CREACION, FECHA_MODIFICACION,ID_ROL,ID_USUARIO)
values
    (sysdate, sysdate,2,id_usuario);

p_status := 'OK';
            p_message := 'Usuario creado correctamente';


when 'updateUser' then
UPDATE kjvargas.usuario
set
    fecha_modificacion = SYSDATE,
    nombre             = p_nombre,
    apellido           = p_apellido,
    email              = p_email,
    password           = p_password,
    estado             = p_estado
where id_usuario       = p_id_usuario;

p_status := 'OK';
            p_message := 'Usuario actualizado correctamente';

when 'deleteUser' then
UPDATE kjvargas.usuario
set
    estado           = null
where id_usuario   = p_id_usuario;

p_status := 'OK';
            p_message := 'Usuario eliminado correctamente';

when 'getAllUsers' then
            OPEN p_result FOR
SELECT
    u.id_usuario, u.nombre,
    u.apellido, u.email,
    u.estado, rol.nombre AS roles
FROM kjvargas.usuario u
         INNER JOIN kjvargas.usuario_rol ur ON u.id_usuario = ur.id_usuario
         INNER JOIN kjvargas.rol rol ON ur.id_rol = rol.id_rol
WHERE u.estado IS NOT NULL
ORDER BY u.id_usuario DESC;

p_status := 'OK';
            p_message := 'Consulta realizada correctamente';

when 'getUserById' then
            OPEN p_result FOR
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

p_status := 'OK';
            p_message := 'Consulta realizada correctamente';
end case;

EXCEPTION
    WHEN OTHERS THEN
        p_status := 'ERROR';
        p_message := 'Error al ejecutar la consulta: ' || SQLERRM;
end;