package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

@Getter
public class AllObjectsForPointLinkTestCreator implements CreatorObject<PointLinksPage, PointLinksPage> {

    private PointLinksPage pointLinksPage;
    private NavigationPage navigationPage;

    private final CreatorObject<PointLinksPage, PointLinksPage> pointLinksObjectsCreator;
    private final CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private final CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;

    private final VirtualPointLinkCriteria criteria;

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage) {
        this(navigationPage, VirtualPointLinkCriteria.change(Script.sourceValue()),
                IdentifierObjectFactory.watchListName());
    }

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage, VirtualPointLinkCriteria pointLinkCriteria) {
        this(navigationPage, pointLinkCriteria,
                IdentifierObjectFactory.watchListName());
    }

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage, VirtualPointLinkCriteria criteria,
                                             WatchListIdentifier watchListIdentifier) {
        this.criteria = criteria;
        this.navigationPage = navigationPage;
        this.dataSourcePointObjectsCreator = new VirtualDataSourcePointObjectsCreator(navigationPage, criteria.getSource(), criteria.getTarget());
        this.watchListObjectsCreator = new WatchListObjectsCreator(navigationPage,
                new WatchListCriteria(watchListIdentifier,
                        criteria.getSource().getIdentifier(),
                        criteria.getTarget().getIdentifier()));
        this.pointLinksObjectsCreator = new PointLinksObjectsCreator(navigationPage, criteria);
    }

    @Override
    public PointLinksPage deleteObjects() {
        watchListObjectsCreator.deleteObjects();
        pointLinksObjectsCreator.deleteObjects();
        dataSourcePointObjectsCreator.deleteObjects();
        return openPage();
    }

    @Override
    public PointLinksPage createObjects() {
        dataSourcePointObjectsCreator.createObjects();
        pointLinksObjectsCreator.createObjects();
        watchListObjectsCreator.createObjects();
        return openPage();
    }

    @Override
    public PointLinksPage openPage() {
        if(pointLinksPage == null) {
            pointLinksPage = navigationPage.openPointLinks();
            return pointLinksPage;
        }
        return pointLinksPage.reopen();
    }

    @Override
    public void reload() {
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }
}
