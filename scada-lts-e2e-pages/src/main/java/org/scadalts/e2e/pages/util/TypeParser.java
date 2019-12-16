package org.scadalts.e2e.pages.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeParser {

    private static final Logger logger = LoggerFactory.getLogger(TypeParser.class);

    public int parseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
            return -1;
        }
    }

    public long parseLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
            return -1;
        }
    }

    public boolean parseBoolean(String value) {
        return Boolean.parseBoolean(value);
    }
}
