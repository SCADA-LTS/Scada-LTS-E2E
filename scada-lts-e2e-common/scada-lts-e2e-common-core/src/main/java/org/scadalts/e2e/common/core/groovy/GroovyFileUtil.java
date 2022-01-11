package org.scadalts.e2e.common.core.groovy;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

@Log4j2
public class GroovyFileUtil {

    public static void collectGroovyFiles(File file, List<File> groovyFiles, Predicate<File> ext) {
        for(File groovyFile: Objects.requireNonNull(file.listFiles())) {
            if(groovyFile.isDirectory()) {
                collectGroovyFiles(groovyFile, groovyFiles, ext);
            }
            if (groovyFile.isFile()) {
                if(groovyFile.getName().toLowerCase().endsWith(".groovy") && ext.test(groovyFile))
                    groovyFiles.add(groovyFile);
            }
        }
    }
}
