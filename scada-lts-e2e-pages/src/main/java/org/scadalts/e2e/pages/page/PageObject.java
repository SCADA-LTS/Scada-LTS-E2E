package org.scadalts.e2e.pages.page;

import org.scadalts.e2e.pages.component.WebElementClickable;

public interface PageObject<T> extends Maximizable, Tab<T> {

    WebElementClickable getSource();
    T getPage();
    String getHeadText();
    String getBodyText();

    @Override
    default T openInNewTab() {
        getSource().openInNewTab();
        return getPage();
    }
}
