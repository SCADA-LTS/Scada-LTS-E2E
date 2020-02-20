package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.util.Objects;

public class WatchListTestsUtil {

    @Getter
    private WatchListPage watchListPage;
    private WatchListCriteria criteria;

    public WatchListTestsUtil(NavigationPage navigationPage, WatchListCriteria criteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.watchListPage = navigationPage.openWatchList();
        this.criteria = criteria;
    }

    public WatchListPage openWatchListPage() {
        return watchListPage.reopen();
    }

    public void clean() {
        watchListPage.reopen().deleteFromWatchList(criteria);
    }
}
