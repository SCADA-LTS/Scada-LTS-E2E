package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;

import java.util.Objects;

public class WatchListTestObjectsUtil implements TestObjectsUtilible<WatchListPage, WatchListPage> {

    private final NavigationPage navigationPage;
    private final DataSourcePointCriteria[] criteria;

    @Getter
    private WatchListPage watchListPage;

    public WatchListTestObjectsUtil(NavigationPage navigationPage, DataSourcePointCriteria... criteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteria = criteria;
        this.navigationPage = navigationPage;
    }

    @Override
    public WatchListPage openPage() {
        if(watchListPage == null) {
            watchListPage = navigationPage.openWatchList();
            return watchListPage;
        }
        return watchListPage.reopen();
    }

    @Override
    public WatchListPage createObjects() {
        WatchListPage page = openPage();
        for (DataSourcePointCriteria cri : criteria) {
            page.addToWatchList(cri);
        }
        return page;
    }

    @Override
    public WatchListPage deleteObjects() {
        WatchListPage page = openPage();
        for (DataSourcePointCriteria cri : criteria) {
            if(page.containsObject(cri))
                page.deleteFromWatchList(cri);
        }
        return page;
    }
}
