package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.utils.Cleanable;

import java.util.Map;
import java.util.Objects;

public class WatchListTestsUtil implements Cleanable {

    private final NavigationPage navigationPage;
    private final SourcePointCriteria[] criteria;
    private final DataSourcesAndPointsPageTestsUtil dataSourcesAndPointsPageTestsUtil;

    @Getter
    private WatchListPage watchListPage;


    public WatchListTestsUtil(NavigationPage navigationPage, SourcePointCriteria... criteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.criteria = criteria;
        Map<DataSourceCriteria, DataPointCriteria[]> map = TestsUtil.createStructure(criteria);
        this.dataSourcesAndPointsPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(navigationPage, map);
        this.navigationPage = navigationPage;
    }

    public void init() {
        this.dataSourcesAndPointsPageTestsUtil.init();
    }

    public WatchListPage openWatchListPage() {
        if(watchListPage == null) {
            watchListPage = navigationPage.openWatchList();
            return watchListPage;
        }
        return watchListPage.reopen();
    }

    public WatchListPage addWatchLists() {
        WatchListPage page = openWatchListPage();
        for (SourcePointCriteria cri : criteria) {
            page.addToWatchList(cri);
        }
        return page;
    }

    @Override
    public void clean() {
        WatchListPage page = openWatchListPage();
        for (SourcePointCriteria cri : criteria) {
            if(page.containsObject(cri))
                page.deleteFromWatchList(cri);
        }
        dataSourcesAndPointsPageTestsUtil.clean();
    }
}
