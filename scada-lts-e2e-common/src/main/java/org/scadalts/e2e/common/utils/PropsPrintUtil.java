package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.text.MessageFormat;
import java.util.Properties;
import java.util.stream.Collectors;

@Log4j2
public class PropsPrintUtil {

    public static void print(final Properties props) {
        String configMessage = msg(props);
        logger.info("config: \n{}", configMessage);
    }


    public static void print(final String msg) {
        logger.info("config: \n{}", msg);
    }

    public static String msg(final Properties props) {
        return props.stringPropertyNames().stream()
                .map(a -> MessageFormat.format("\n\t{0}={1}", a, props.getProperty(a, "")))
                .collect(Collectors.joining());
    }
}
