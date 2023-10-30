package com.kjvargas.admusers.Configs.Procedures;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProcedureFileScanner {
    private final ResourcePatternResolver resourcePatternResolver;

    public ProcedureFileScanner(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

    public List<String> scanProcedureFiles() {
        List<String> procedureFileNames = new ArrayList<>();
        try {
            Resource[] resources = resourcePatternResolver.getResources("classpath:/Procedures/*.sql");
            for (Resource resource : resources) {
                procedureFileNames.add(resource.getFilename());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return procedureFileNames;
    }
}
