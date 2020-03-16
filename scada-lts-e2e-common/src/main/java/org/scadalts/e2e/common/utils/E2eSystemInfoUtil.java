package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.common.logo.AsciiHeaders;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Properties;

@Log4j2
public abstract class E2eSystemInfoUtil {

    private E2eSystemInfoUtil() {}

    public static String getInfo() {
        try {
            return _systemInfo();
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }

    private static String _systemInfo() throws IOException {
        Properties properties = new Properties();
        properties.load(FileUtil.getResourceAsStream("e2e-version.properties"));
        Properties system = System.getProperties();
        return _header(properties, system);
    }

    private static String _header(Properties properties, Properties system) {
        return MessageFormat.format(
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
    }

    public static void printHeaderWithConfig(E2eConfig config) {
        logger.info("-----------------------------------------------------1");
        logger.info("{}\n", E2eSystemInfoUtil.getInfo());
        logger.info("-----------------------------------------------------2");
        logger.info(config);
        logger.info("-----------------------------------------------------3");
    }
}
