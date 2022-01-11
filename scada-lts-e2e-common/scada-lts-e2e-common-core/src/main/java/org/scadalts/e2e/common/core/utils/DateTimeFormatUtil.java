package org.scadalts.e2e.common.core.utils;

import java.time.LocalDateTime;

public class DateTimeFormatUtil {

    public static String now() {
        return LocalDateTime.now().toString().replace(":", "_");
    }
}
