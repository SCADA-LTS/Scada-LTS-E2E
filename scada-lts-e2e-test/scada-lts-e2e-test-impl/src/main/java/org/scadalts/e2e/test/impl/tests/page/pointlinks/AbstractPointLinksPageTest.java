package org.scadalts.e2e.test.impl.tests.page.pointlinks;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.scadalts.e2e.page.impl.criteria.PointLinkCriteria;
import org.scadalts.e2e.page.impl.criteria.SourcePointCriteria;
import org.scadalts.e2e.page.impl.dict.DataPointType;
import org.scadalts.e2e.page.impl.dict.EventType;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.PointLinksTestsUtil;

public abstract class AbstractPointLinksPageTest {

    private static PointLinksPage pointLinksPage;
    private static PointLinksTestsUtil pointLinksTestsUtil;

    private static PointLinkCriteria criteria;
    private static SourcePointCriteria source;
    private static SourcePointCriteria target;

    @BeforeClass
    public static void setup() {
        source = SourcePointCriteria.virtualDataSource(DataPointType.NUMERIC, "12");
        target = SourcePointCriteria.virtualDataSource(DataPointType.NUMERIC, "123");
        criteria = PointLinkCriteria.builder()
                                .source(source)
                                .target(target)
                                .type(EventType.CHANGE)
                                .script("return source.value;")
                                .build();
        pointLinksTestsUtil = new PointLinksTestsUtil(E2eAbstractRunnable.getNavigationPage(), criteria);
        pointLinksTestsUtil.init();
        pointLinksPage = pointLinksTestsUtil.openPointLinksPage();
    }

    @AfterClass
    public static void clean() {
        pointLinksTestsUtil.clean();
    }

    public static PointLinksPage getPointLinksPage() {
        return pointLinksPage;
    }

    public static PointLinksTestsUtil getPointLinksTestsUtil() {
        return pointLinksTestsUtil;
    }

    public static PointLinkCriteria getCriteria() {
        return criteria;
    }

    public static SourcePointCriteria getSource() {
        return source;
    }

    public static SourcePointCriteria getTarget() {
        return target;
    }
}
