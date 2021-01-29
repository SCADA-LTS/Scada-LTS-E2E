package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Getter
public class AllObjectsForPointLinkTestCreator implements CreatorObject<PointLinksPage, WatchListPage> {

    private PointLinksPage pointLinksPage;
    private final NavigationPage navigationPage;

    private final CreatorObject<PointLinksPage, PointLinksPage> pointLinksObjectsCreator;
    private final CreatorObject<WatchListPage, WatchListPage> watchListObjectsCreator;
    private final CreatorObject<DataSourcesPage, DataSourcesPage> dataSourcePointObjectsCreator;

    private final PointLinkCriteria criteria;

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage) {
        this(navigationPage, PointLinkCriteria.change(Script.sourceValue()),
                IdentifierObjectFactory.watchListName());
    }

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage, PointLinkCriteria pointLinkCriteria) {
        this(navigationPage, pointLinkCriteria,
                IdentifierObjectFactory.watchListName());
    }

    public AllObjectsForPointLinkTestCreator(NavigationPage navigationPage, PointLinkCriteria criteria,
                                             WatchListIdentifier watchListIdentifier) {
        this.criteria = criteria;
        this.navigationPage = navigationPage;
        this.dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage,
                criteria.getSource(), criteria.getTarget());
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
    public WatchListPage createObjects() {
        dataSourcePointObjectsCreator.createObjects();
        pointLinksObjectsCreator.createObjects();
        return watchListObjectsCreator.createObjects();
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
