package org.scadalts.e2e.page.core.util;


import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;

@Log4j2
public abstract class TypeParser {

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

    public static int parseIntValueFormatted(String value) {
        try {
            return new BigDecimal(value).intValue();
        } catch (Exception throwable) {
            logger.warn(throwable.getMessage(), throwable);
            throw throwable;
        }
    }
}
