package org.scadalts.e2e.page.core.utils;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.*;

@Log4j2
public abstract class E2eUtil {

    public static void acceptAlert() {
        try {
            sleep(500);
            confirm();
        } catch (Exception ex) {

        }
    }

    public static void dismissAlert() {
        try {
            sleep(500);
            dismiss();
        } catch (Exception ex) {

        }
    }
}
