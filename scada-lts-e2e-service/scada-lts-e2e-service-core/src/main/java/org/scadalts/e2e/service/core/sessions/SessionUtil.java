package org.scadalts.e2e.service.core.sessions;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public class SessionUtil {

    public static Optional<String> getSessionIdFrom(Response response) {
        Cookie sessionCookie = response.getCookies().get(SessionCookieKey.JSESSIONID.name());
        if(sessionCookie == null) {
            List<Object> setCookieHeaders = response.getHeaders().get("Set-Cookie");
            if(setCookieHeaders == null || setCookieHeaders.isEmpty()) {
                return Optional.empty();
            }
            for(Object header: setCookieHeaders) {
                String headerAsString = String.valueOf(header);
                if(headerAsString.contains(SessionCookieKey.JSESSIONID.name()))
                    return getSessionId(headerAsString);
            }
        }
        return Optional.ofNullable(sessionCookie.getValue());
    }

    private static Optional<String> getSessionId(String setCookie) {
        if(StringUtils.isEmpty(setCookie))
            return Optional.empty();
        String[] setCookieOptions = setCookie.split(";");
        for(String option: setCookieOptions) {
            if(option.contains(SessionCookieKey.JSESSIONID.name())) {
                String[] sessionId = option.split("=");
                if(!StringUtils.isEmpty(sessionId[0]) && sessionId[0].equalsIgnoreCase(SessionCookieKey.JSESSIONID.name())) {
                    if(sessionId.length == 2) {
                        return Optional.ofNullable(sessionId[1]);
                    }
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }
}
