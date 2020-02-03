package org.scadalts.e2e.common.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.types.AuthType;

import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public class E2eConfiguration {

    public volatile static AuthType authType = AuthType.FORM;
    public volatile static String userName = "admin";
    public volatile static String password = "admin";
    public volatile static URL baseUrl;

    static {
        try {
            baseUrl = new URL("http://localhost:8080/ScadaBR/");
        } catch (MalformedURLException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
