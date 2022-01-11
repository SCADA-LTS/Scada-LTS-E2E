package groovy

import org.junit.Before
import org.junit.BeforeClass
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage

import static org.scadalts.e2e.page.impl.groovy.NavigationUtil.*
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*
import static org.scadalts.e2e.common.core.utils.DateTimeFormatUtil.*
import static org.scadalts.e2e.page.impl.groovy.OperationUtil.*

/*
GraphicalViewsPage.viewNames
GraphicalViewsPage.selectViewBy(String)
GraphicalViewsPage.selectViewBy(IdentifierObject)
GraphicalViewsPage.waitOnLoadedBackground()
GraphicalViewsPage.screenshot(String)
GraphicalViewsPage.executeJs(String)
GraphicalViewsPage.acceptAlert()
DateTimeFormatUtil.now()
NavigationUtil.openGraphicalViews()
 */

class Test {

    @BeforeClass
    static void preconfig() {
        headless(true)
    }

    @Before
    void config() {
    }

    @org.junit.Test
    void test() {
    }
}