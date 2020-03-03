package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Getter
public class AllObjectsForPointLinkTestCreator implements CreatorObject<PointLinksPage, WatchListPage> {

    private PointLinksPage pointLinksPage;
    private final NavigationPage navigationPage;

    private final CreatorObject<PointLinksPage, PointLinksPage> pointLinksTestsCreator;
    private final CreatorObject<WatchListPage, WatchListPage> watchListTestsCreator;
    private final CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcesAndPointsPageTestsCreator;

    private final PointLinkCriteria criteria;

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage) {
        this(navigationPage, PointLinkCriteria.change());
    }

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage, PointLinkCriteria criteria) {
        this.criteria = criteria;
        this.navigationPage = navigationPage;
        this.dataSourcesAndPointsPageTestsCreator = new DataSourcePointObjectsCreator(navigationPage,
                criteria.getSource(), criteria.getTarget());
        this.watchListTestsCreator = new WatchListObjectsCreator(navigationPage, criteria.getSource(), criteria.getTarget());
        this.pointLinksTestsCreator = new PointLinksObjectsCreator(navigationPage, criteria);
    }

    @Override
    public PointLinksPage deleteObjects() {
        watchListTestsCreator.deleteObjects();
        pointLinksTestsCreator.deleteObjects();
        dataSourcesAndPointsPageTestsCreator.deleteObjects();
        return openPage();
    }

    @Override
    public WatchListPage createObjects() {
        dataSourcesAndPointsPageTestsCreator.createObjects();
        pointLinksTestsCreator.createObjects();
        return watchListTestsCreator.createObjects();
    }

    @Override
    public PointLinksPage openPage() {
        if(pointLinksPage == null) {
            pointLinksPage = navigationPage.openPointLinks();
            return pointLinksPage;
        }
        return pointLinksPage.reopen();
    }
}
