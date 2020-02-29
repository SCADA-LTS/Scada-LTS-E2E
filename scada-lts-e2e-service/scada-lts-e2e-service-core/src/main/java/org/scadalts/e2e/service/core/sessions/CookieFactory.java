package org.scadalts.e2e.service.core.sessions;

import javax.ws.rs.core.Cookie;

public class CookieFactory {

    public static Cookie newSessionCookie(String sessionCookie) {
        return new Cookie(SessionCookieKey.JSESSIONID.name(), sessionCookie);
    }
}
