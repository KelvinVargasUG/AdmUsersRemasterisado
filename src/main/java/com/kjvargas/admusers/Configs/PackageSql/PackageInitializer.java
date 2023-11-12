package com.kjvargas.admusers.Configs.PackageSql;

import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
public class PackageInitializer {
    private final JdbcTemplate jdbcTemplate;
    private final UsuarioService usuarioService;
    private final List<String> packageFileNames;

    @Value("${user-email-admin}")
    private String emailAdmin;

    @Value("${user-password-admin}")
    private String passwordAdmin;

    @Autowired
    public PackageInitializer(JdbcTemplate jdbcTemplate, UsuarioService usuarioService, PackageFileScanner procedureFileScanner) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioService = usuarioService;
        this.packageFileNames = procedureFileScanner.scanPackageFiles();
    }

    @PostConstruct
    public void createPackages() {
        log.info("Inicio de creacion de package");
        for (String executeFileName : packageFileNames) {
            try {
                String script = new String(Files.readAllBytes(Paths.get("src/main/resources/Package/" + executeFileName)));
                jdbcTemplate.execute(script);
                log.info("Creando package: " + executeFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("Fin de creacion de package");
        log.info("Creando usuario administrador");
        usuarioService.createUserAdmin(emailAdmin, passwordAdmin);
        log.info("Usuario administrador creado");
    }
}
