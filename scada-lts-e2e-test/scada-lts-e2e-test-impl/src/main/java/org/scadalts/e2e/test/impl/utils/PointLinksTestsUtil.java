package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criteria.DataPointCriteria;
import org.scadalts.e2e.page.impl.criteria.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criteria.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.core.utils.Cleanable;

import java.util.Map;

@Log4j2
public class PointLinksTestsUtil implements Cleanable {

    private final NavigationPage navigationPage;
    @Getter
    private final PointLinkCriteria pointLinkCriteria;
    private final DataSourcesAndPointsPageTestsUtil dataSourcesAndPointsPageTestsUtil;

    @Getter
    private PointLinksPage pointLinksPage;

    public PointLinksTestsUtil(NavigationPage navigationPage, PointLinkCriteria pointLinkCriteria) {
        this.pointLinkCriteria = pointLinkCriteria;
        Map<DataSourceCriteria, DataPointCriteria[]> map = TestsUtil.createStructure(new SourcePointCriteria[]{pointLinkCriteria.getSource(), pointLinkCriteria.getTarget()});
        this.dataSourcesAndPointsPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(navigationPage, map);
        this.navigationPage = navigationPage;
    }

    public PointLinksTestsUtil(NavigationPage navigationPage) {
        this.pointLinkCriteria = PointLinkCriteria.change();
        Map<DataSourceCriteria, DataPointCriteria[]> map = TestsUtil.createStructure(new SourcePointCriteria[]{pointLinkCriteria.getSource(), pointLinkCriteria.getTarget()});
        this.dataSourcesAndPointsPageTestsUtil = new DataSourcesAndPointsPageTestsUtil(navigationPage, map);
        this.navigationPage = navigationPage;
    }

    public PointLinksPage openPointLinksPage() {
        if(pointLinksPage == null) {
            pointLinksPage = navigationPage.openPointLinks();
            return pointLinksPage;
        }
        return pointLinksPage.reopen();
    }

    public PointLinksPage addPointLink() {
        return getPointLinksPage().openPointLinkCreator()
                .setPoints(pointLinkCriteria)
                .setScript(pointLinkCriteria.getScript())
                .setEventType(pointLinkCriteria.getType())
                .savePointLink();
    }

    public void init() {
        dataSourcesAndPointsPageTestsUtil.init();
    }

    public void cleanPointLink() {
        PointLinksPage pointLinksPage = openPointLinksPage();
        if(pointLinksPage.containsObject(pointLinkCriteria)) {
            pointLinksPage.openPointLinkEditor(pointLinkCriteria)
                    .deletePointLink();
        }
    }

    @Override
    public void clean() {
        cleanPointLink();
        dataSourcesAndPointsPageTestsUtil.clean();
    }

}
