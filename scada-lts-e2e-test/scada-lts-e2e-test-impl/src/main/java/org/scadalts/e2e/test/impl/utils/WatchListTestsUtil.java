package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.util.Objects;

public class WatchListTestsUtil {

    @Getter
    private WatchListPage dataSourcesPage;

    public WatchListTestsUtil(NavigationPage navigationPage) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.dataSourcesPage = navigationPage.openWatchList();
    }

    public WatchListPage openWatchListPage() {
        return dataSourcesPage.reopen();
    }
}
