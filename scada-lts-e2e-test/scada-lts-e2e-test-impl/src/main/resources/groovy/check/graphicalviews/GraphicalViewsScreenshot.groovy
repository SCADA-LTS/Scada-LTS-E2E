package groovy.check.graphicalviews

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test

import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*
import static org.scadalts.e2e.page.impl.groovy.NavigationUtil.*
import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.*
import static org.scadalts.e2e.page.impl.groovy.OperationUtil.*
import static org.scadalts.e2e.page.impl.groovy.EditorUtil.*

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

class GraphicalViewsScreenshot {

	@BeforeClass
    static void preconfig() {
        headless(true)
    }

	@Before
    void config() {}

	@Test
    void test() {
		def page = openGraphicalViews()
		
		page.getViewNames().forEach{a ->
			page.selectViewBy(a)
					.waitOnLoadedBackground()
					.screenshot(a)
		}
    }
}