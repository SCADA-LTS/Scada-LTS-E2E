package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Log4j2
public class WatchListObjectsCreator implements CreatorObject<WatchListPage, WatchListPage> {

    private NavigationPage navigationPage;
    private final WatchListCriteria[] criterias;

    @Getter
    private WatchListPage watchListPage;

    public WatchListObjectsCreator(NavigationPage navigationPage, WatchListCriteria... criterias) {
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
        for (WatchListCriteria criteria : criterias) {
            if(!page.containsObject(criteria.getIdentifier())) {
                logger.info("create object: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getClass().getSimpleName());
                page.addWatchList(criteria);
            }
            page.selectWatchList(criteria.getIdentifier());
            for (DataSourcePointIdentifier identifier : criteria.getDataSourcePointIdentifiers()) {
                if(!page.isVisibleWatchListUnit(identifier)) {
                    logger.info("create object: {}, class: {}", identifier.getValue(),
                            identifier.getClass().getSimpleName());
                    page.addDataToWatchList(identifier);
                }
            }
        }
        return page;
    }

    @Override
    public WatchListPage deleteObjects() {
        WatchListPage page = openPage();
        for (WatchListCriteria criteria : criterias) {
            if(page.containsObject(criteria.getIdentifier())) {
                logger.info("delete object: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getClass().getSimpleName());
                page.deleteWatchList(criteria.getIdentifier());
            }
        }
        return page;
    }

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }
}
