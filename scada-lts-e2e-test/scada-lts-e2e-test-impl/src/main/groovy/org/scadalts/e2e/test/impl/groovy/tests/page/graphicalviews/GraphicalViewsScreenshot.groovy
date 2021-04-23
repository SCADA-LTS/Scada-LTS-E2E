package org.scadalts.e2e.test.impl.groovy.tests.page.graphicalviews

import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage

import static org.scadalts.e2e.page.impl.groovy.NavigationUtil.*
import static org.scadalts.e2e.page.impl.groovy.ConfigurationUtil.*
import static org.scadalts.e2e.common.core.utils.DateTimeFormatUtil.*
import static org.scadalts.e2e.page.impl.groovy.OperationUtil.*
import static org.scadalts.e2e.test.impl.groovy.CreatorUtil.*

import org.scadalts.e2e.page.impl.criterias.DataPointCriteria
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria

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

	def dataSourceCriteria;
	def dataPointCriteria;

    void preconfig() {
        headless(true)
    }

    void config() {
		dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond()
		dataPointCriteria = DataPointCriteria.numericNoChange()
		
		create(dataSourceCriteria, dataPointCriteria)
    }

    void test() {
		def editor = openDataPointEditor(dataSourceCriteria, dataPointCriteria)
		
		editor.xid = 'test'
		editor.save()

		screenshot(editor)

		def page = openGraphicalViews()

		
		page.executeJs("alert('dziala')")
		page.acceptAlert()
		
		page.viewNames.forEach{a -> 
		println(a)
		page.selectViewBy(a)
		.waitOnLoadedBackground()
		.screenshot(a)
		}
    }
}