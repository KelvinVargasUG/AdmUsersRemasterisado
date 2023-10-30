package com.kjvargas.admusers.Configs.Procedures;

import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class ProcedureInitializer {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private UsuarioService usuarioService;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @Autowired
    public ProcedureInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @PostConstruct
    public void createProcedure() {
        try {
            String script = new String(Files.readAllBytes(Paths.get("src/main/resources/data.sql")));
            jdbcTemplate.execute(script);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            usuarioService.createUserAdmin(emailAdmin, passwordAdmin);
        }
    }
}
