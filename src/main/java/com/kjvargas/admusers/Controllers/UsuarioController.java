package com.kjvargas.admusers.Controllers;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> createUser(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.createUser(usuario));
    }

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        return ResponseEntity.ok(usuarioService.findAllUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findByIdUser(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findByIdUser(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.updateUser(usuario, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.deleteUser(id));
    }
}
