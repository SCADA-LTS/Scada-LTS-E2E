package org.scadalts.e2e.page.core.javascripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import java.util.Optional;

public interface JavascriptExecutable {

    Logger LOGGER = LogManager.getLogger(JavascriptExecutable.class);

    default Object executeJs(String script) {
        try {
            RemoteWebDriver webDriver = E2eWebDriverProvider.getDriver();
            return webDriver.executeScript(script);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return new Object();
        }
    }

    default <T> Optional<T> executeJs(JavascriptProvider script, Class<T> returnType) {
        try {
            Object object = executeJs(script.getScriptToExecute());
            T result = returnType.cast(object);
            return Optional.ofNullable(result);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    default long executeJsLong(JavascriptProvider script) {
        try {
            Object result = executeJs(script.getScriptToExecute());
            return (long) result;
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2L;
        }
    }

    default int executeJsInt(JavascriptProvider script) {
        try {
            Object result = executeJs(script.getScriptToExecute());
            return (int) result;
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2;
        }
    }

    default double executeJsDouble(JavascriptProvider script) {
        try {
            Object result = executeJs(script.getScriptToExecute());
            return (double) result;
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2d;
        }
    }

    default boolean executeJsBoolean(JavascriptProvider script) {
        try {
            Object result = executeJs(script.getScriptToExecute());
            return (boolean) result;
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return false;
        }
    }

    default Optional<Boolean> executeJsOptBoolean(JavascriptProvider script) {
        return executeJs(script, Boolean.class);
    }

    default Optional<String> executeJsOptString(JavascriptProvider script) {
        return executeJs(script, String.class);
    }

    default void executeJs(JavascriptProvider script) {
        executeJs(script.getScriptToExecute());
    }
}
