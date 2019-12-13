package org.scadalts.e2e.pages.component;

import static com.codeborne.selenide.Selenide.switchTo;

public class E2eUtils {

    public static void acceptAlert() {
        try {
            switchTo().alert().accept();
        } catch (Exception ex) {

        }
    }
}
