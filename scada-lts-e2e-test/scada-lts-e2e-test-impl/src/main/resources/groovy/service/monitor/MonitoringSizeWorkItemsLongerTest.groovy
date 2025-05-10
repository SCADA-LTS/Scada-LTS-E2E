package groovy.service.monitor

import org.codehaus.groovy.tools.shell.util.MessageSource
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.scadalts.e2e.service.core.services.E2eResponse
import org.scadalts.e2e.service.core.services.get.GetConfig

import java.text.MessageFormat

import static org.junit.Assert.assertNotNull
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

class MonitoringSizeWorkItemsLongerTest {

    private static MessageSource messageSource;

    @BeforeClass
    static void preconfig() {
        headless(true)
        pageMode(false)
        messageSource = new MessageSource()
    }

    @Before
    void config() {
    }

    @Test
    void test() {
        //given:
        int longer = 1500
        int limit = 100
        GetConfig config = new GetConfig("/api/work-items/longer/{0}/", String.valueOf(longer))

        //when:
        E2eResponse<Map<String, Object>> response = UniversalServiceUtil.getAsMap(config, 3000, Object.class)
        Map<String, Object> type = response.getValue()
        Integer size = type.get("size")

        //then:
        assertNotNull("The response is missing the \"size\" property", size)
        assertTrue(messageSource.format("Number of Work Items taking longer than {0} [ms] exceeded {1}", longer, limit), size <= limit)
    }
}