package org.scadalts.e2e.test.impl.groovy;

import groovy.lang.GroovyObject;
import lombok.Getter;

import java.io.File;

@Getter
class GroovyExecute {
    private final GroovyObject groovyObject;
    private final File groovyFile;

    public GroovyExecute(GroovyObject groovyObject, File groovyFile) {
        this.groovyObject = groovyObject;
        this.groovyFile = groovyFile;
    }

    @Override
    public String toString() {
        return "" + groovyFile.getName();
    }
}
