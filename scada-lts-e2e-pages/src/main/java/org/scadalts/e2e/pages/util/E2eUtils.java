package org.scadalts.e2e.pages.util;

import static com.codeborne.selenide.Selenide.switchTo;

public class E2eUtils {

    public static void acceptAlert() {
        try {
            switchTo().alert().accept();
        } catch (Exception ex) {

        }
    }
}
