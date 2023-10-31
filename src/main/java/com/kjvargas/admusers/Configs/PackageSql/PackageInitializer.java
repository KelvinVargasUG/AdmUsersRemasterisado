package com.kjvargas.admusers.Configs.PackageSql;

import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        for (String executeFileName : packageFileNames) {
            try {
                String script = new String(Files.readAllBytes(Paths.get("src/main/resources/Package/" + executeFileName)));
                jdbcTemplate.execute(script);
                System.out.println("Creating package: " + executeFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        usuarioService.createUserAdmin(emailAdmin, passwordAdmin);
    }
}
