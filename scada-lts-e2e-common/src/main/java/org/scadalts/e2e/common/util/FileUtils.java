package org.scadalts.e2e.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileUtils {

    private static Logger logger = LogManager.getLogger(FileUtils.class);

    public static File createNewFileInFileSystem(String fileName) {
        try {
            Path path = Paths.get(fileName);
            logger.debug("path: {}", path);
            InputStream inputStream = getResourceAsStream(fileName);
            if(path.toFile().exists()) {
                return path.toFile();
            }
            _createDirs(path);
            Files.copy(inputStream, path);
            return path.toFile();
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public static InputStream getResourceAsStream(String fileName) {
        try {
            return FileUtils.class.getClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private static void _createDirs(Path path) {
        Path parent = path.getParent();
        if(Objects.nonNull(parent)) {
            File dir = path.getParent().toFile();
            dir.mkdirs();
        }
    }
}
