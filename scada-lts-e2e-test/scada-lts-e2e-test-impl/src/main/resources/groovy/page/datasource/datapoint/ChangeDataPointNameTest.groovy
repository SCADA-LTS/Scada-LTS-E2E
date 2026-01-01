package groovy.page.datasource.datapoint

import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier
import org.scadalts.e2e.page.impl.dicts.DataPointType

import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*
import static org.scadalts.e2e.page.impl.groovy.NavigationUtil.*
import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.*
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

class ChangeDataPointNameTest {

    def dataSourceCriteria;

    @BeforeClass
    static void preconfig() {
        headless(true)
    }

    @Before
    void config() {
        dataSourceCriteria = UpdateDataSourceCriteria.virtualDataSourceSecond()
        create(dataSourceCriteria)
    }

    @Test
    void test() {

        //given:
        VirtualDataPointCriteria dataPointCriteria = VirtualDataPointCriteria.noChange(new DataPointIdentifier("test1", DataPointType.BINARY))
        create(dataSourceCriteria, dataPointCriteria)
        def nameExpected = 'test2';
        def editor = openDataPointEditor(dataSourceCriteria, dataPointCriteria)

        //when:
        editor.name = nameExpected
        editor.save()

        //then:
        def nameResult = openDataSources()
                .openDataSourceEditor(dataSourceCriteria)
                .openDataPointEditor(dataPointCriteria)
                .name

        Assert.assertEquals(nameExpected, nameResult);
    }
}