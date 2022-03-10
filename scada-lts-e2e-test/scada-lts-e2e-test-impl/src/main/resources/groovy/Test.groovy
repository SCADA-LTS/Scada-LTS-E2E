package groovy

import org.junit.Before
import org.junit.BeforeClass

import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.headless
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.pageMode

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
        pageMode(false)
    }

    @Before
    void config() {
    }

    @org.junit.Test
    void test() {
    }
}