package org.scadalts.e2e.pages.type;

import org.scadalts.e2e.pages.component.WebElementClickable;

public interface CanBeOpenedNewTab<T> {

    WebElementClickable getSource();
    T getPage();

    default T openInNewTab() {
        getSource().openInNewTab();
        return getPage();
    }
}
