package org.scadalts.e2e.cli.parsers;

import org.apache.logging.log4j.Level;
import picocli.CommandLine.*;

public class LogLevelParser implements ITypeConverter<Level> {

    @Override
    public Level convert(String name) throws Exception {
        return Level.getLevel(name);
    }
}
