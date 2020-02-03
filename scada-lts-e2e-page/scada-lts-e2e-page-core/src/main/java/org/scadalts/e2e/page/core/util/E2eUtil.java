package org.scadalts.e2e.page.core.util;

import lombok.extern.log4j.Log4j2;

import static com.codeborne.selenide.Selenide.switchTo;

@Log4j2
public abstract class E2eUtil {

    public static void acceptAlert() {
        try {
            switchTo().alert().accept();
        } catch (Exception ex) {

        }
    }
}
