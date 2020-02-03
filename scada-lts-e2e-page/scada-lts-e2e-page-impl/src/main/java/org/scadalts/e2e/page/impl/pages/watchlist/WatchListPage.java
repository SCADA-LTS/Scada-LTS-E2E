package org.scadalts.e2e.page.impl.pages.watchlist;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

public class WatchListPage extends MainPageObjectAbstract<WatchListPage> {

    public static final String TITLE = "Watch list";

    public WatchListPage(SelenideElement source) {
        super(source, TITLE);
    }

    @Override
    public WatchListPage getPage() {
        return this;
    }
}