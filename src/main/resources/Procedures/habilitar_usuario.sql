CREATE OR REPLACE PROCEDURE kjvargas.habilitar_usuario(p_id_user IN NUMBER)
AS
BEGIN
UPDATE kjvargas.usuario U SET U.estado = 'A' WHERE U.id_usuario = p_id_user;
END habilitar_usuario;