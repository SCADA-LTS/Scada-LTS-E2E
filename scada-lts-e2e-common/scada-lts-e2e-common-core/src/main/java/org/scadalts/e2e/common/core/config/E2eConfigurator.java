package org.scadalts.e2e.common.core.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.scadalts.e2e.common.core.groovy.GroovyFileUtil.collectGroovyFiles;
import static org.scadalts.e2e.common.core.utils.FileUtil.getFileFromJar;

@Log4j2
public class E2eConfigurator {

    private static E2eConfig CONFIG;

    public static void init(E2eConfig config) {
        if(!Objects.isNull(CONFIG)) {
            return;
        }
        if(Objects.isNull(config)) {
            logger.warn("Config is null");
            return;
        }
        CONFIG = ConfigHandler.handle(config);
        E2eConfiguration.authType = config.getAuthType();
        E2eConfiguration.baseUrl = config.getUrlAppBeingTested();
        E2eConfiguration.password = config.getPassword();
        E2eConfiguration.username = config.getUserName();
        E2eConfiguration.logLevel = config.getLogLevel();
        E2eConfiguration.checkAuthentication = config.isCheckAuthentication();
        org.apache.logging.log4j.core.config.Configurator.setRootLevel(config.getLogLevel());
        org.apache.logging.log4j.core.config.Configurator.setAllLevels("org.apache.logging.log4j", config.getLogLevel());
    }

    public static void configGroovy() {
        getFileFromJar("groovy" ).ifPresent(a -> {
/*
            //try (FileSystem fs = FileSystems.newFileSystem(resource.toURI(), Collections.emptyMap())) {
                logger.info("fileSystem: {}", a);
            try {
                List<Path> collect = Files.walk(a.toPath())
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
                logger.info(collect);
                for(Path path: collect) {
                    getFileFromJar(path.toString());
                }
            } catch (IOException e) {
                logger.info(e.getMessage(), e);
            }
                /*for(FileStore fileStore: fs.getFileStores()) {
                    logger.info(" {}", fileStore.name());
                }*/
            //}
        });
    }

    public static void init() {
        init(ConfigHandler.getConfig());
    }
}
