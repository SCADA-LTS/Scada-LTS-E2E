package org.scadalts.e2e.page.core.javascripts;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import java.util.Optional;

public interface JavascriptExecutable {

    Logger LOGGER = LogManager.getLogger(JavascriptExecutable.class);

    default Optional<Object> executeJs(String script) {
        try {
            RemoteWebDriver webDriver = E2eWebDriverProvider.getDriver();
            return Optional.ofNullable(webDriver.executeScript(script));
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    default <T> Optional<T> executeJs(JavascriptProvider script, Class<T> returnType) {
        try {
            Optional<Object> object = executeJs(script.getScriptToExecute());
            if(object.isPresent()) {
                T result = returnType.cast(object.get());
                return Optional.of(result);
            }
            return Optional.empty();
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return Optional.empty();
        }
    }

    default long executeJsLong(JavascriptProvider script) {
        try {
            return (long) executeJs(script.getScriptToExecute()).orElse(-1L);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2L;
        }
    }

    default int executeJsInt(JavascriptProvider script) {
        try {
            return (int) executeJs(script.getScriptToExecute()).orElse(-1);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2;
        }
    }

    default double executeJsDouble(JavascriptProvider script) {
        try {
            return (double) executeJs(script.getScriptToExecute()).orElse(-1.0);
        } catch (Throwable ex) {
            LOGGER.error(ex.getMessage(), ex);
            return -2d;
        }
    }

    default boolean executeJsBoolean(JavascriptProvider script) {
        try {
            return (boolean) executeJs(script.getScriptToExecute()).orElse(false);
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
