package org.scadalts.e2e.test.impl.groovy;

import groovy.lang.GroovyObject;
import lombok.Getter;

import java.io.File;

@Getter
class GroovyExecute {
    private final GroovyObject groovyObject;
    private final File groovyFile;
    private final DataPointTestData data;

    public GroovyExecute(GroovyObject groovyObject, File groovyFile, DataPointTestData data) {
        this.groovyObject = groovyObject;
        this.groovyFile = groovyFile;
        this.data = data;
    }

    @Override
    public String toString() {
        return "" + groovyFile.getName() + (!data.isEmpty() ? " [point: " + data + "]" : "");
    }
}
