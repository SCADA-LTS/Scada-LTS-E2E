package groovy.service.monitor

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.scadalts.e2e.service.core.services.E2eResponse
import org.scadalts.e2e.service.core.services.get.GetConfig

import java.text.MessageFormat

import static org.junit.Assert.assertTrue
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.headless
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.pageMode


import org.scadalts.e2e.service.core.utils.UniversalServiceUtil

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

class MonitoringThreadsStatusBlockedTest {

    @BeforeClass
    static void preconfig() {
        headless(true)
        pageMode(false)
    }

    @Before
    void config() {
    }

    @Test
    void test() {
        //given:
        String blockedStatus = "BLOCKED"
        int limit = 0
        GetConfig config = new GetConfig("/api/threads/states/state/{0}/", blockedStatus)

        //when:
        E2eResponse<List<Object>> response = UniversalServiceUtil.getAsList(config, 3000, Object.class)
        List<Object> threads = response.getValue()

        //then:
        assertTrue(MessageFormat.format("Number of Threads with Status {0} exceeded {1}", blockedStatus, limit), threads.size() <= limit)
    }
}