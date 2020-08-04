package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
public class FileUtil {

    public static File getFileFromJar(String fileName) {
        try {
            logger.info("getFileFromJar: {}", fileName);
            Path path = Paths.get(fileName);
            if(path.toFile().exists()) {
                return path.toFile();
            }
            return _createNewFileInFileSystem(fileName);
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public static File getFileFromFileSystem(Path path) {
        try {
            logger.info("getFileFromFileSystem: {}", path);
            File file = path.toFile();
            if(file.exists()) {
                logger.debug("file exists: {}", file);
                return file;
            }
            boolean created = file.createNewFile();
            logger.debug("file: {}, created: {}", file, created);
            return file;
        } catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public static File getFileFromFileSystem(String fileName) {
        Path path = Paths.get(fileName);
        return getFileFromFileSystem(path);
    }

    public static InputStream getResourceAsStream(String fileName) {
        try {
            return FileUtil.class.getClassLoader().getResourceAsStream(fileName);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new IllegalStateException(e);
        }
    }

    public static File zip(File file) {
        String zipFileName = file.getName() + ".zip";
        try (ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            Files.copy(file.toPath(), zipOut);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return new File(zipFileName);
    }

    public static List<String> readLines(Path path) {
        try {
            return Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private static File _createNewFileInFileSystem(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        boolean dirsCreated = _createDirs(path);
        InputStream inputStream = getResourceAsStream(fileName);
        Files.copy(inputStream, path);
        return path.toFile();
    }

    private static boolean _createDirs(Path path) {
        Path parent = path.getParent();
        if(Objects.nonNull(parent)) {
            File dir = path.getParent().toFile();
            return dir.mkdirs();
        }
        return false;
    }
}
