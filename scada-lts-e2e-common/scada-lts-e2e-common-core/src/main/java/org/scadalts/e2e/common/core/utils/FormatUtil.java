package org.scadalts.e2e.common.core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class FormatUtil {
    public static String unformat(Object input) {
        String value = String.valueOf(input);
        if(StringUtils.isNumeric(value))
            return String.valueOf(new BigDecimal(value).doubleValue());
        return value;
    }
}
