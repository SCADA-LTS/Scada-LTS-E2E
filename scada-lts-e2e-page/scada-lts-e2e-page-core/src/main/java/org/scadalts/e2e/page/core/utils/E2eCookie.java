package org.scadalts.e2e.page.core.utils;

import lombok.ToString;
import org.openqa.selenium.Cookie;

import java.util.Date;
import java.util.Map;

@ToString
public class E2eCookie {

    private final Cookie cookie;

    public E2eCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public String getName() {
        return cookie.getName();
    }

    public String getValue() {
        return cookie.getValue();
    }

    public String getDomain() {
        return cookie.getDomain();
    }

    public String getPath() {
        return cookie.getPath();
    }

    public boolean isSecure() {
        return cookie.isSecure();
    }

    public boolean isHttpOnly() {
        return cookie.isHttpOnly();
    }

    public Date getExpiry() {
        return cookie.getExpiry();
    }

    public void validate() {
        cookie.validate();
    }

    public Map<String, Object> toJson() {
        return cookie.toJson();
    }
}
