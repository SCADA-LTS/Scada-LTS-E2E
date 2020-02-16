package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.logo.AsciiHeaders;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Log4j2
public abstract class E2eVersionUtil {

    public static String getVersion() {
        try {
            return _getVersion();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    private static String _getVersion() throws IOException {
        Properties properties = new Properties();
        properties.load(FileUtil.getResourceAsStream("e2e-version.properties"));
        Properties system = System.getProperties();
        String version = MessageFormat.format(
                "{0}\n\n" +
                        "JVM: {1} ({2} {3} {4})\n" +
                        "OS: {5} {6} {7}\n",
                MessageFormat.format(AsciiHeaders.MAIN_HEADER_WITH_VERSION, properties.getProperty("test.e2e.project.version", "")),
                system.getProperty("java.version",""),
                system.getProperty("java.vendor", ""),
                system.getProperty("java.vm.name", ""),
                system.getProperty("java.vm.version", ""),
                system.getProperty("os.name", ""),
                system.getProperty("os.version", ""),
                system.getProperty("os.arch", ""));
        return version;
    }
}