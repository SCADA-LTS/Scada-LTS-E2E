package groovy.check.website

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.scadalts.e2e.service.core.services.E2eResponse
import org.scadalts.e2e.service.core.services.get.GetConfig

import static org.junit.Assert.assertNotNull
import static org.junit.Assert.assertTrue
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.headless
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.baseUrl
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.pageMode

import org.scadalts.e2e.service.core.utils.UniversalServiceUtil

class WebsiteTest {

    private static String BASE_URL = "http://www.scada-lts.com";

    @BeforeClass
    static void preconfig() {
        headless(true)
        pageMode(false)
        baseUrl(BASE_URL)
    }

    @Before
    void config() {
    }

    @Test
    void test() {
        //given:
        GetConfig config = new GetConfig("/")
        String msg = "Page " + BASE_URL + " is not available!"

        //when:
        E2eResponse<String> response = UniversalServiceUtil.getAsString(config, 3000)
        String value = response.getValue()

        //then:
        assertNotNull(msg, value)
        assertTrue(msg, value.contains("Moved Permanently"))
    }
}