package org.scadalts.e2e.common.core.config;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.common.core.types.AuthType;

import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public class E2eConfiguration {

    public static AuthType authType = AuthType.FORM;
    public static String username = "admin1";
    public static String password = "admin1";
    public static Level logLevel = Level.DEBUG;
    public static URL baseUrl;
    public static String sessionId;
    public static boolean checkAuthentication = false;
    public static boolean loginDisabled = true;

    static {
        try {
            baseUrl = new URL("http://localhost:8080/ScadaBR");
        } catch (MalformedURLException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
