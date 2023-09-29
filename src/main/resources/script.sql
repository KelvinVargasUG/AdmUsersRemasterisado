alter session set "_ORACLE_SCRIPT"=true;

--Crea usuario
CREATE USER kjvargas IDENTIFIED BY 12345;

--Privilegio de admin
GRANT DBA TO kjvargas;

--privilegios de coneccion
GRANT CONNECT, RESOURCE TO kjvargas;

CREATE OR REPLACE PROCEDURE saveUsuario
    (p_nombre in varchar2(255),
     p_apellido in varchar2(255),
     p_email in varchar2(255),
     p_password in varchar2(255)
     )
as
begin
insert into kjvargas.USUARIO (NOMBRE, APELLIDO, EMAIL, PASSWORD, ESTADO, FECHA_CREACION, FECHA_MODIFICACION)
values (p_nombre, p_apellido, p_email, p_password,'A',sysdate,sysdate);
end;