package com.kjvargas.admusers.Entitys.Usuario;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kjvargas.admusers.Entitys.CamposObligatorios;

import javax.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@NamedStoredProcedureQuery(
        name = "Rol.findAll",
        procedureName = "kjvargas.find_all_rol",
        resultClasses = Rol.class
)
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
public class Rol extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", unique = true, nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Column(name = "descripcion")
    private String descripcion;

}