package com.kjvargas.admusers.Configs.PackageSql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PackageFileScanner {
    private final ResourcePatternResolver resourcePatternResolver;

    public PackageFileScanner(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<String> scanPackageFiles() {
        List<String> procedureFileNames = new ArrayList<>();
        try {
            log.info("Inicio de escaneo de package");
            Resource[] resources = resourcePatternResolver.getResources("classpath:/Package/*.sql");
            for (Resource resource : resources) {
                procedureFileNames.add(resource.getFilename());
                log.info("package encontrados: " + resource.getFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Fin de escaneo de package");
        return procedureFileNames;
    }
}
