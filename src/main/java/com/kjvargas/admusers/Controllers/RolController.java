package com.kjvargas.admusers.Controllers;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import com.kjvargas.admusers.Services.Usuario.RolService;
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

    @GetMapping
    public ResponseEntity<List<Rol>> findAllRol() {
        return ResponseEntity.ok(rolService.findAllRoles());
    }

    @GetMapping("/no_asignados/user/{id}")
    public ResponseEntity<List<Rol>> findByUserUnassignedRoles(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(rolService.findByUserUnassignedRoles(id));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Rol>> findRolByUserId(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(rolService.findRolByUserId(id));
    }
}
