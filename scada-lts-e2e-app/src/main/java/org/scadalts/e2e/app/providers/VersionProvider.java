package org.scadalts.e2e.app.providers;

import org.scadalts.e2e.common.util.FileUtils;
import picocli.CommandLine.*;

import java.util.Properties;

public class VersionProvider implements IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        Properties properties = new Properties();
        properties.load(FileUtils.getResourceAsStream("e2e-version.properties"));
        return new String[]{properties.getProperty("test.e2e.project.version")};
    }
}
