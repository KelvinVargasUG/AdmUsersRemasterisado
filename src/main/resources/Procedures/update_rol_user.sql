create or replace procedure kjvargas.update_rol_user(p_id_rol in number,p_id_user in number) as
begin
update KJVARGAS.USUARIO_ROL u set u.id_rol = p_id_rol where u.ID_USUARIO = p_id_user;
end;