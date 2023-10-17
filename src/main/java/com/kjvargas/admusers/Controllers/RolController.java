package com.kjvargas.admusers.Controllers;

import com.kjvargas.admusers.Entitys.Usuario.Rol;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/roles")
public class RolController {

    @GetMapping
    public ResponseEntity<List<Rol>> findAllRol() {
        return null;
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Rol> findByUserUnassignedRoles(@PathVariable Long id) {
        return null;
    }

}
