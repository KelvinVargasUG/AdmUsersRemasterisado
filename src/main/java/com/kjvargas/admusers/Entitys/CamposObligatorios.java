package com.kjvargas.admusers.Entitys;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@MappedSuperclass
public class CamposObligatorios {

    @Pattern(regexp = "[AI]", message = "{app.fiel.estado.error}")
    @Size(max = 1, message = "1 {app.fiel.cantidadCaracteres.error}")
    @Column(name = "estado", columnDefinition = "VARCHAR(1) DEFAULT 'A'")
    private String estado;


    @Column(precision = 8, scale = 0, name = "fecha_creacion", updatable = false)
    private Long fechaCreacion;

    @Column(precision = 8, scale = 0 , name = "fecha_modificacion", insertable = false)
    private Long fechaModificacion;

}
