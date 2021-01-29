package org.scadalts.e2e.test.impl.utils;

import java.util.Objects;

public class RegexUtil {

    public static final String DATE_PSEUDO_ISO_REGEX = "^(2[0-9][2-9][0-9])-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1]) ([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])";
    public static final String DATE_ISO_REGEX = "^(201[4-9]|202[0-9])-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])T([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])Z";

    public static final String DATA_POINT_NOTIFIER_NAME_REGEX = "(.*\\sST\\s.*|.*\\sAL\\s.*)";
    public static final String DATA_POINT_ALARM_NOTIFIER_NAME_REGEX = "(.*\\sAL\\s.*)";
    public static final String DATA_POINT_STORUNG_NOTIFIER_NAME_REGEX = "(.*\\sST\\s.*)";

    public static String getDateIso(String raw) {
        if(Objects.isNull(raw))
            return "";
        return raw.replace(" ", "T") + "Z";
    }
}
