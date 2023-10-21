package com.kjvargas.admusers.SecurityJwt.Controller;

import com.kjvargas.admusers.Entitys.Usuario.Usuario;
import com.kjvargas.admusers.SecurityJwt.ConfigurationsJwt.JwtUtils;
import com.kjvargas.admusers.SecurityJwt.Entitys.JwtRequest;
import com.kjvargas.admusers.SecurityJwt.Entitys.JwtResponse;
import com.kjvargas.admusers.SecurityJwt.SecuritySpring.UserDetailService;
import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController()
@CrossOrigin(origins = "*")
@RequestMapping("/api/auth")
public class authController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    UsuarioService usuarioService;

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getEmail(), jwtRequest.getPassword());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        autenticar(jwtRequest.getEmail(), jwtRequest.getPassword());

        UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RuntimeException("Usuario Deshabilitado " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Credenciales Invalidas");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        try {
            if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("El password es obligatorio");
            }
            return ResponseEntity.ok(usuarioService.createUser(usuario));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
/*
    @GetMapping("/actual-usuario")
    public ResponseEntity<DTOLogin> obtenerUsuarioActual(Principal principal) {
        User user = (User) this.userDetailService.loadUserByUsername(principal.getName());
        List<DTOLogin.AuthorityDTO> authorityDTOs = user.getAuthorities().stream()
                .map(authority -> new DTOLogin.AuthorityDTO(authority.getAuthority()))
                .collect(Collectors.toList());

        DTOLogin userDTO = new DTOLogin(user.getUsername(), authorityDTOs);

        return ResponseEntity.ok(userDTO);
    }

     */
}
