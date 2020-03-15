package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class WatchListObjectsCreator implements CreatorObject<WatchListPage, WatchListPage> {

    private final NavigationPage navigationPage;
    private final DataSourcePointCriteria[] criterias;

    @Getter
    private WatchListPage watchListPage;

    public WatchListObjectsCreator(NavigationPage navigationPage, DataSourcePointCriteria... criterias) {
        this.criterias = criterias;
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
        for (DataSourcePointCriteria criteria : criterias) {
            if(!page.isVisibleWatchList() || !page.containsObject(criteria.getIdentifier())) {
                logger.info("create object: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getClass().getSimpleName());
                page.addToWatchList(criteria.getIdentifier());
            }
        }
        return page;
    }

    @Override
    public WatchListPage deleteObjects() {
        WatchListPage page = openPage();
        for (DataSourcePointCriteria criteria : criterias) {
            if(page.containsObject(criteria.getIdentifier())) {
                logger.info("delete object: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getClass().getSimpleName());
                page.deleteFromWatchList(criteria.getIdentifier());
            }
        }
        return page;
    }
}
