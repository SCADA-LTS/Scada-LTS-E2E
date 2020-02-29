package org.scadalts.e2e.page.core.utils;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

@Log4j2
public abstract class E2eUtil {

    public static void acceptAlert() {
        try {
            sleep(500);
            switchTo().alert().accept();
        } catch (Exception ex) {

        }
    }

    public static void dismissAlert() {
        try {
            sleep(500);
            switchTo().alert().dismiss();
        } catch (Exception ex) {

        }
    }
}