package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criterias.PointLinkCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;

@Getter
public class AllObjectsForPointLinkTestsUtil implements TestObjectsUtilible<PointLinksPage, WatchListPage>{

    private PointLinksPage pointLinksPage;
    private final NavigationPage navigationPage;

    private final TestObjectsUtilible<PointLinksPage, PointLinksPage> pointLinksTestsUtil;
    private final TestObjectsUtilible<WatchListPage, WatchListPage> watchListTestsUtil;
    private final TestObjectsUtilible<DataSourcesPage, EditDataSourceWithPointListPage> dataSourcesAndPointsPageTestsUtil;

    private final PointLinkCriteria criteria;

    public AllObjectsForPointLinkTestsUtil(NavigationPage navigationPage) {
        this(navigationPage, PointLinkCriteria.change());
    }

    public AllObjectsForPointLinkTestsUtil(NavigationPage navigationPage, PointLinkCriteria criteria) {
        this.criteria = criteria;
        this.navigationPage = navigationPage;
        this.dataSourcesAndPointsPageTestsUtil = new DataSourcePointTestObjectsUtil(navigationPage,
                criteria.getSource(), criteria.getTarget());
        this.watchListTestsUtil = new WatchListTestObjectsUtil(navigationPage, criteria.getSource(), criteria.getTarget());
        this.pointLinksTestsUtil = new PointLinksTestObjectsUtil(navigationPage, criteria);
    }

    @Override
    public PointLinksPage deleteObjects() {
        watchListTestsUtil.deleteObjects();
        pointLinksTestsUtil.deleteObjects();
        dataSourcesAndPointsPageTestsUtil.deleteObjects();
        return openPage();
    }

    @Override
    public WatchListPage createObjects() {
        dataSourcesAndPointsPageTestsUtil.createObjects();
        pointLinksTestsUtil.createObjects();
        return watchListTestsUtil.createObjects();
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
