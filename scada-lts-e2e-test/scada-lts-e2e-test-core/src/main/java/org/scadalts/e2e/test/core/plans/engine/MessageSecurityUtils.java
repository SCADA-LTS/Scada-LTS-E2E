package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfiguration;

import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public final class MessageSecurityUtils {

    private MessageSecurityUtils() {}

    public static String safe(String message) {
        if(message == null)
            return "";

        if (message.contains(E2eConfiguration.baseUrl.toString()) && message.contains("login")) {
            return message.replaceAll(getDangerous(), getSafe());
        }
        return message;
    }

    private static String getSafe() {
        return E2eConfiguration.baseUrl + "/login/" + E2eConfiguration.username + "/*******";
    }

    private static String getDangerous() {
        try {
            return new URL(E2eConfiguration.baseUrl + "/login/" + E2eConfiguration.username + "/" + E2eConfiguration.password).toString();
        } catch (MalformedURLException e) {
            logger.warn(e.getMessage(), e);
            return "";
        }
    }
}
