package org.scadalts.e2e.common.core.utils;

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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
public class FileUtil {

    public static Optional<File> getFileFromJar(String fileName) {
        try {
            logger.info("getFileFromJar: {}", fileName);
            Path path = Paths.get(fileName.trim());
            if(path.toFile().exists()) {
                return Optional.ofNullable(path.toFile());
            }
            return _createNewFileInFileSystem(fileName);
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<File> getFileFromFileSystem(Path path) {
        try {
            logger.info("getFileFromFileSystem: {}", path);
            if(Files.exists(path)) {
                logger.info("file exists: {}", path);
                return Optional.ofNullable(path.toFile());
            }
            if(!Files.notExists(path)) {
                throw new IllegalArgumentException("File access denied: " + path);
            }
            File file = path.toFile();
            boolean created = file.createNewFile();
            logger.info("file: {}, created: {}", file, created);
            return Optional.of(file);
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static Optional<File> getFileFromFileSystem(String fileName) {
        Path path = Paths.get(fileName.trim());
        return getFileFromFileSystem(path);
    }

    public static Optional<InputStream> getResourceAsStream(String fileName) {
        try {
            return Optional.ofNullable(FileUtil.class.getClassLoader().getResourceAsStream(fileName.trim()));
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return Optional.empty();
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

    private static Optional<File> _createNewFileInFileSystem(String fileName) throws IOException {
        Path path = Paths.get(fileName.trim());
        boolean dirsCreated = _createDirs(path);
        Optional<InputStream> inputStream = getResourceAsStream(fileName.trim());
        if(inputStream.isPresent()) {
            Files.copy(inputStream.get(), path);
            return Optional.ofNullable(path.toFile());
        }
        return Optional.empty();
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