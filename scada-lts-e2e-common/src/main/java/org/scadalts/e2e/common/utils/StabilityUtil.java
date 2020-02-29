package org.scadalts.e2e.common.utils;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class StabilityUtil {

    private final static long INTERVAL_MS = 2000;
    private final static int LIMIT = 100;

    public static void sleep() {
        try {
            Thread.sleep(INTERVAL_MS);
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    public static boolean isExceededLimit(int i) {
        return i > LIMIT;
    }

    public static boolean isExceededTimeout(Timeout timeout, long time) {
        return System.currentTimeMillis() - time > timeout.getValue();
    }

    @Data
    public static class Timeout {
        private final long value;
    }
}
