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
@RequestMapping("/auth")
public class auth {

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
            e.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }

        UserDetails userDetails = this.userDetailService.loadUserByUsername(jwtRequest.getEmail());
        String token = this.jwtUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void autenticar(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("Usuario Deshabilitado " + e.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales Invalidas " + e.getMessage());
        }
    }

    /*
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrarUsuario(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.createUsuario(usuario,"Registre"));
    }

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
