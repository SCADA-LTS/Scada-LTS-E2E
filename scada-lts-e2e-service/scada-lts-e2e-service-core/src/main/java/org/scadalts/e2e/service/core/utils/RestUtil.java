package org.scadalts.e2e.service.core.utils;

import javax.ws.rs.core.MediaType;

public final class RestUtil {

    private RestUtil() {}

    public static MediaType getJsonUtf8MediaType() {
        return MediaType.APPLICATION_JSON_TYPE.withCharset("UTF-8");
    }
}
