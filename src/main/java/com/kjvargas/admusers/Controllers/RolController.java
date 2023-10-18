package com.kjvargas.admusers.Controllers;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Services.Usuario.RolService;
import com.kjvargas.admusers.Services.Usuario.UsuarioRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private UsuarioRolService usuarioRolService;

    @GetMapping
    public ResponseEntity<?> findAllRol() {
        try {
            return ResponseEntity.ok(rolService.findAllRoles());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/no_asignados/user/{id}")
    public ResponseEntity<?> findByUserUnassignedRoles(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioRolService.findByUserUnassignedRoles(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> findRolByUserId(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioRolService.findRolByUserId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
