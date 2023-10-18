package com.kjvargas.admusers.Entitys;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
@MappedSuperclass
public class CamposObligatorios {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Pattern(regexp = "[AI]", message = "{app.fiel.estado.error}")
    @Size(max = 1, message = "1 {app.fiel.cantidadCaracteres.error}")
    @Column(name = "estado", columnDefinition = "VARCHAR(1) DEFAULT 'A'")
    private String estado;

    @JsonIgnore
    @Column(precision = 8, scale = 0, name = "fecha_creacion", updatable = false)
    private Date fechaCreacion;

    @JsonIgnore
    @Column(precision = 8, scale = 0 , name = "fecha_modificacion", insertable = false)
    private Date fechaModificacion;

}
