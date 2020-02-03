package org.scadalts.e2e.webservice.core.session;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import java.util.Optional;

public class SessionUtil {

    public static Optional<String> getSessionIdFrom(Response response) {
        Cookie sessionCookie = response.getCookies().get(SessionCookieKey.JSESSIONID.name());
        if(sessionCookie == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(sessionCookie.getValue());
    }
}
