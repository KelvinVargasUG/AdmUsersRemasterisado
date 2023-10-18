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
    public ResponseEntity<?> createUser(@Valid @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.createUser(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllUser() {
        try {
            return ResponseEntity.ok(usuarioService.findAllUser());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdUser(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.findByIdUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody Usuario usuario, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.updateUser(usuario, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable Long id) {
        try {
            return ResponseEntity.ok(usuarioService.deleteUser(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/comprobar_exit_email")
    public ResponseEntity<?> comprobarExistenciaEmail(@Valid @RequestParam("email") String email) {
        try {
            return ResponseEntity.ok(usuarioService.comprobarExistenciaEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
