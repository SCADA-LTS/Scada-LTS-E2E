package org.scadalts.e2e.pages.page.watchlist;

import com.codeborne.selenide.SelenideElement;
import org.scadalts.e2e.pages.page.PageObjectAbstract;

public class WatchListPage extends PageObjectAbstract<WatchListPage> {

    public WatchListPage(SelenideElement source) {
        super(source);
    }

    @Override
    public WatchListPage getPage() {
        return this;
    }
}