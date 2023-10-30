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
import java.util.List;

@Component
public class ProcedureInitializer {
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioService usuarioService;
    private final List<String> procedureFileNames;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @Autowired
    public ProcedureInitializer(JdbcTemplate jdbcTemplate, UsuarioService usuarioService, ProcedureFileScanner procedureFileScanner) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioService = usuarioService;
        this.procedureFileNames = procedureFileScanner.scanProcedureFiles();
    }

    @PostConstruct
    public void createProcedures() {
        for (String procedureFileName : procedureFileNames) {
            try {
                String script = new String(Files.readAllBytes(Paths.get("src/main/resources/Procedures/" + procedureFileName)));
                jdbcTemplate.execute(script);
                System.out.println("Creating procedure: " + procedureFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        usuarioService.createUserAdmin(emailAdmin, passwordAdmin);
    }
}
