CREATE OR REPLACE PROCEDURE kjvargas.updateusuario(p_id_usuario IN NUMBER,
                                                   p_nombre IN VARCHAR2,
                                                   p_apellido IN VARCHAR2,
                                                   p_email IN VARCHAR2,
                                                   p_estado IN VARCHAR2)
AS
BEGIN
UPDATE kjvargas.usuario
SET fecha_modificacion = sysdate,
    nombre             = p_nombre,
    apellido           = p_apellido,
    email              = p_email,
    estado             = p_estado
WHERE id_usuario = p_id_usuario;
END updateusuario;