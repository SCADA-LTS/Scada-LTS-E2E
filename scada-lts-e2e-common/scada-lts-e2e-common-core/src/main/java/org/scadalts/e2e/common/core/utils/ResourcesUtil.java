package org.scadalts.e2e.common.core.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Log4j2
public class ResourcesUtil {

    public static List<File> getFilesFromJar(String dir, Class<?> base) {
        try {
            logger.info("getFilesFromJar: {}", dir);
           // URL resource = base.getClassLoader().getResource("");
           // URLClassPath resource1 = new URLClassPath(new URL[]{resource});
           // resource1.getResource(dir);
           // logger.info("pathFromJar: {}", resource);
            //logger.info("pathFromJar: {}", resource.toURI());
           // List<Path> collect;
           /* File file = null;
            try {
                file = new File(resource.toURI());
            } catch (URISyntaxException e) {
                file = new File(resource.getPath());
            }
            try (FileSystem fs = FileSystems.newFileSystem(resource.toURI(), Collections.emptyMap(), base.getClassLoader())) {
                logger.info("fileSystem: {}", fs);
                collect = Files.walk(file.toPath())
                        .filter(Files::isRegularFile)
                        .collect(Collectors.toList());
                logger.info("files: {}", collect);
                for(FileStore fileStore: fs.getFileStores()) {
                    logger.info(" {}", fileStore.name());
                }
            }*/
/*
            List<File> collect = new ArrayList<>();
            try (ZipInputStream zip = new ZipInputStream(resource.openStream())) {
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    if (name.contains("test-impl")) {
                        File dir1 = new File(Paths.get(name).getParent().toString());
                        if (!dir1.exists())
                            dir1.mkdirs();
                        Files.copy(zip, Paths.get(name), StandardCopyOption.REPLACE_EXISTING);

                    }
                }
            }*/
            URL resource = base.getClassLoader().getResource("groovy");
            List<File> collect = createSystemFiles("test-impl", resource);

            //collect.stream().flatMap(a -> createSystemFiles(base, "groovy", a.getAbsolutePath()).stream()).collect(Collectors.toList());

            logger.info("filesFromJar: {}", collect);
            /*for(File file1: collect) {
                getFileFromJar(resource.getFile());
            }*/


            return collect.stream().flatMap(a -> {
                try {
                    return createSystemFiles("groovy", a.toURI().toURL()).stream();
                } catch (MalformedURLException e) {
                    logger.error(e.getMessage(), e);
                    return Stream.empty();
                }
            }).collect(Collectors.toList());

            //return unzip(resource1.getResource(dir));
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public static List<File> createSystemFiles(String key, URL resource) {
        List<File> result = new ArrayList<>();
        logger.info("pathFromJar: {}", resource);
        try (ZipInputStream zip = new ZipInputStream(resource.openStream())) {
            ZipEntry entry;
            while ((entry = zip.getNextEntry()) != null) {
                String name = entry.getName();
                Path path = Paths.get(name);
                if (name.contains(key)) {
                    File target = new File(path.getParent() == null ? File.separator : path.getParent().toString());
                    if (!target.exists())
                        target.mkdirs();
                    Files.copy(zip, path, StandardCopyOption.COPY_ATTRIBUTES);
                    result.add(path.toFile());
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }
/*
    private static List<File> unzip(Resource resource) {
        List<File> files = new ArrayList<>();
        try {
            ZipInputStream zin = new ZipInputStream(resource.getInputStream());
            ZipEntry entry = null;
            while((entry = zin.getNextEntry()) != null) {
                File file = new File(entry.getName());
                FileOutputStream os = new FileOutputStream(file);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    os.write(c);
                }
                os.close();
                files.add(file);
            }
        } catch (IOException e) {
            logger.error("Error while extract the zip: "+e);
        }
        return files;
    }*/
}
