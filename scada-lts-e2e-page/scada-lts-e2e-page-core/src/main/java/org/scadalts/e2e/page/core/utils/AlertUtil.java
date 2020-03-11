package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.javascripts.JavascriptWindow;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.executeJavaScript;

@Log4j2
public abstract class AlertUtil {

    public static void acceptAlert() {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScript());
    }

    public static void dismissAlert() {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScript());
    }

    public static void acceptAlertAfterClick(SelenideElement selenideElement) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScript());
        selenideElement.click();
    }

    public static void acceptAlertAfter(Procedure procedure) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScript());
        procedure.invoke();
    }

    public static <T> void acceptAlertAfter(Consumer<T> consumer, T value) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScript());
        consumer.accept(value);
    }


    public static void dismissAlertAfterClick(SelenideElement selenideElement) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScript());
        selenideElement.click();
    }

    public static void dismissAlertAfter(Procedure procedure) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScript());
        procedure.invoke();
    }

    public static <T> void dismissAlertAfter(Consumer<T> consumer, T value) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScript());
        consumer.accept(value);
    }

    @FunctionalInterface
    public interface Procedure {
        void invoke();
    }
}
