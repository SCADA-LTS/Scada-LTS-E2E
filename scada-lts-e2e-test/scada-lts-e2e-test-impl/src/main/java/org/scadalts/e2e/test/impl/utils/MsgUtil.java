package org.scadalts.e2e.test.impl.utils;

import java.text.MessageFormat;

public class MsgUtil {

    public static String notExistsShouldBeInObjectMsg(String object, String objectName, String shouldBeInObject, String shouldBeInObjectName) {
        return notExistsMsg(object, objectName) + shouldBeInObjectMsg(object, shouldBeInObject, shouldBeInObjectName);
    }

    public static String shouldBeInObjectMsg(String object, String shouldBeInObject, String shouldBeInObjectName) {
        return MessageFormat.format(" {0} should be in {1}: {2}", object, shouldBeInObject, shouldBeInObjectName);
    }

    public static String notExistsMsg(String object, String objectName) {
        return MessageFormat.format(" {0} named: {1} is not exists!", object, objectName);
    }
}
