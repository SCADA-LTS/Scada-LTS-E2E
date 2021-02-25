package org.scadalts.e2e.common.core.utils;

import org.scadalts.e2e.common.core.config.E2eConfig;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Log4j2
public class E2eSystemInfoUtil {

    private final AsciiHeaders asciiHeaders;

    public E2eSystemInfoUtil(AsciiHeaders asciiHeaders) {
        this.asciiHeaders = asciiHeaders;
    }

    public String getInfo() {
        try {
            return _systemInfo();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    private String _systemInfo() throws IOException {
        Properties properties = new Properties();
        FileUtil.getResourceAsStream("e2e-version.properties").ifPresent(a -> {
            try {
                properties.load(a);
            } catch (IOException e) {
                properties.put("test.e2e.project.version", "Unknown");
            }
        });
        Properties system = System.getProperties();
        return _header(properties, system);
    }

    private String _header(Properties properties, Properties system) {
        return MessageFormat.format(
                    "{0}\n\n" +
                            "JVM: {1} ({2} {3} {4})\n" +
                            "OS: {5} {6} {7}\n",
                    MessageFormat.format(asciiHeaders.getMainHeaderWithVersion(), properties.getProperty("test.e2e.project.version", "")),
                    system.getProperty("java.version",""),
                    system.getProperty("java.vendor", ""),
                    system.getProperty("java.vm.name", ""),
                    system.getProperty("java.vm.version", ""),
                    system.getProperty("os.name", ""),
                    system.getProperty("os.version", ""),
                    system.getProperty("os.arch", ""));
    }

    public void printHeaderWithConfig(E2eConfig config) {
        logger.info("-----------------------------------------------------1");
        logger.info("{}\n", getInfo());
        logger.info("-----------------------------------------------------2");
        logger.info(config);
        logger.info("-----------------------------------------------------3");
    }
}
