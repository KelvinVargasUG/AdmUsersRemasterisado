package com.kjvargas.admusers.Entitys.Usuario;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kjvargas.admusers.Entitys.CamposObligatorios;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Usuario extends CamposObligatorios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;

    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "nombre")
    private String nombre;

    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "apellido")
    private String apellido;

    @Email
    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "email")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "{app.fiel.notEmpty.error}")
    @Column(name = "password")
    private String password;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"), 
                                    inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"))
    private List<Rol> roles;

}
