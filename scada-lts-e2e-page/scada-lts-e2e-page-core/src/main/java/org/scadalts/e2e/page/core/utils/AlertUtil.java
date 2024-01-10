package org.scadalts.e2e.page.core.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.javascripts.JavascriptWindow;

import java.util.function.Consumer;

import static com.codeborne.selenide.Selenide.executeJavaScript;

@Log4j2
public abstract class AlertUtil {

    public static void acceptAlert() {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
    }

    public static void acceptAlertSlow() {
        try {
            Selenide.switchTo().alert().accept();
        } catch (Throwable th) {

        }
    }

    public static void dismissAlert() {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScriptToExecute());
    }

    public static void acceptAfterClick(SelenideElement selenideElement) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        selenideElement.click();
    }

    public static void acceptAfterClickSlow(SelenideElement selenideElement) {
        //executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        selenideElement.click();
        acceptAlertSlow();
    }

    public static void acceptAfterClick(E2eWebElement selenideElement) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        selenideElement.click();
    }

    public static void acceptAfterClickSlow(E2eWebElement selenideElement) {
        //executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        selenideElement.click();
        acceptAlertSlow();
    }

    public static void acceptAlert(Procedure procedure) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        procedure.invoke();
        try {
            Selenide.switchTo().alert().accept();
        } catch (Throwable th) {

        }
    }

    public static <T> void acceptAlert(Consumer<T> consumer, T value) {
        executeJavaScript(JavascriptWindow.ACCEPT_ALERT.getScriptToExecute());
        consumer.accept(value);
    }


    public static void dismissAfterClick(SelenideElement selenideElement) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScriptToExecute());
        selenideElement.click();
    }

    public static void dismissAfterClick(E2eWebElement selenideElement) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScriptToExecute());
        selenideElement.click();
    }

    public static void dismissAlert(Procedure procedure) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScriptToExecute());
        procedure.invoke();
    }

    public static <T> void dismissAlert(Consumer<T> consumer, T value) {
        executeJavaScript(JavascriptWindow.DISMISS_ALERT.getScriptToExecute());
        consumer.accept(value);
    }

    @FunctionalInterface
    public interface Procedure {
        void invoke();
    }
}
