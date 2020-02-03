package org.scadalts.e2e.common.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.stream.Collectors;

public class PropsPrintUtil {

    private static Logger logger = LogManager.getLogger(PropsPrintUtil.class);

    public static void print(final Properties props) {
        String configMessage = msg(props);
        logger.info("system config: \n{}", configMessage);
    }


    public static void print(final String msg) {
        logger.info("system config: \n{}", msg);
    }

    public static String msg(final Properties props) {
        return props.stringPropertyNames().stream()
                .map(a -> MessageFormat.format("\n{0}={1}", a, props.getProperty(a, "")))
                .collect(Collectors.joining());
    }
}
