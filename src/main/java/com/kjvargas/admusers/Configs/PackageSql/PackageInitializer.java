package com.kjvargas.admusers.Configs.PackageSql;

import com.kjvargas.admusers.Services.Usuario.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
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

    private final ResourceLoader resourceLoader;

    @Autowired
    public PackageInitializer(JdbcTemplate jdbcTemplate, UsuarioService usuarioService, PackageFileScanner procedureFileScanner, ResourceLoader resourceLoader) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioService = usuarioService;
        this.packageFileNames = procedureFileScanner.scanPackageFiles();
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void createPackages() {
        log.info("Inicio de creacion de package");
        for (String executeFileName : packageFileNames) {
            try {
                Resource resource = resourceLoader.getResource("classpath:/Package/" + executeFileName);
                String script = new String(resource.getInputStream().readAllBytes());
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
