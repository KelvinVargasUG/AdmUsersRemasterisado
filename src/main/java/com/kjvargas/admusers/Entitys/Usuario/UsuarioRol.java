package com.kjvargas.admusers.Entitys.Usuario;

import com.kjvargas.admusers.Entitys.CamposObligatorios;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usuario_rol")
public class UsuarioRol extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "id_usuario")
    private Integer idUsuario;

}
