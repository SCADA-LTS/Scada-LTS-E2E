package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Log4j2
public class FileUtil {

    public static File getFile(String fileName) {
        try {
            Path path = Paths.get(fileName);
            logger.debug("path: {}", path);
            if(path.toFile().exists()) {
                return path.toFile();
            }
            return _createNewFileInFileSystem(fileName, path);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public static InputStream getResourceAsStream(String fileName) {
        try {
            return FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    private static File _createNewFileInFileSystem(String fileName, Path path) throws IOException {
        _createDirs(path);
        InputStream inputStream = getResourceAsStream(fileName);
        Files.copy(inputStream, path);
        return path.toFile();
    }

    private static void _createDirs(Path path) {
        Path parent = path.getParent();
        if(Objects.nonNull(parent)) {
            File dir = path.getParent().toFile();
            dir.mkdirs();
        }
    }
}
