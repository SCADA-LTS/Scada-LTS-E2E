package org.scadalts.e2e.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    private static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static File toFile(String fileName) {
        try {
            InputStream inputStream = getResourceAsStream(fileName);
            Path path = Paths.get(fileName);
            if(path.toFile().exists())
                return path.toFile();
            Files.copy(inputStream, path);
            return path.toFile();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    static InputStream getResourceAsStream(String fileName) {
        try {
            return FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }
}
