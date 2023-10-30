CREATE OR REPLACE PROCEDURE kjvargas.deleteusuario(p_id_usuario IN NUMBER)
AS
BEGIN
UPDATE kjvargas.usuario
SET estado = NULL
WHERE id_usuario = p_id_usuario;
END deleteusuario;