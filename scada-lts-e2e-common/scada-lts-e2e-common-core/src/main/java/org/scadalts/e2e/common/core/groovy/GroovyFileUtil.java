package org.scadalts.e2e.common.core.groovy;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.*;

@Log4j2
public class GroovyFileUtil {

    public static void collectGroovyFiles(File file, List<File> groovyFiles) {
        for(File groovyFile: Objects.requireNonNull(file.listFiles())) {
            if(groovyFile.isDirectory()) {
                collectGroovyFiles(groovyFile, groovyFiles);
            }
            if (groovyFile.isFile()) {
                if(groovyFile.getName().toLowerCase().endsWith(".groovy"))
                    groovyFiles.add(groovyFile);
            }
        }
    }
}
