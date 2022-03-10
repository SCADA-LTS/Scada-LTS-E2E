package groovy.page.datasource.datapoint

import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.scadalts.e2e.service.core.services.E2eResponse
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse
import org.scadalts.e2e.test.impl.groovy.DataPointTestData
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil

import java.text.MessageFormat

import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*

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

class CheckPointValueTest {

    @BeforeClass
    static void preconfig() {
        headless(false)
        pageMode(false)
    }

    @Before
    void config() {}

    @Test
    void test(DataPointTestData point) {

        //given:
        PointValueParams pointValueParams = new PointValueParams(point.getXid());

        //when:
        E2eResponse<PointValueResponse> getResponse = TestWithoutPageUtil.getDataPointValue(pointValueParams);

        //then:
        Assert.assertNotNull(MessageFormat.format("datapoint: {0} (xid: {1}) is not exists", point.getName(), point.getXid()), getResponse.getValue())
        Assert.assertNotNull(MessageFormat.format("datapoint: {0} (xid: {1}) is not exists", point.getName(), point.getXid()), getResponse.getValue().getFormattedValue())

        double var1 = Double.parseDouble(getResponse.getValue().getFormattedValue());
        Assert.assertTrue(MessageFormat.format("datapoint: {0} (xid: {1}) value exceeds the upper limit: {2} and was: {3}", point.getName(), point.getXid(), point.getMax(), var1), var1 < point.getMax())
        Assert.assertTrue(MessageFormat.format("datapoint: {0} (xid: {1}) value exceeds the lower limit: {2} and was: {3}", point.getName(), point.getXid(), point.getMin(), var1), var1 > point.getMin())
    }
}