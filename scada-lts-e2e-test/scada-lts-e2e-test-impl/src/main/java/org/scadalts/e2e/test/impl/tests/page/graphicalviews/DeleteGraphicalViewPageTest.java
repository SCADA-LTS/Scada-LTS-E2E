package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class DeleteGraphicalViewPageTest {

    private final GraphicalViewIdentifier viewName = IdentifierObjectFactory.viewName();

    private GraphicalViewCriteria criteria;
    private GraphicalViewObjectsCreator graphicalViewObjectsCreator;
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void createView() {
        criteria = GraphicalViewCriteria.criteria(viewName);
        graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(TestWithPageUtil.openNavigationPage(), criteria);
        graphicalViewsPageSubject = graphicalViewObjectsCreator.createObjects();
    }

    @After
    public void clean() {
        if(graphicalViewObjectsCreator != null) {
            GraphicalViewsPage page = graphicalViewObjectsCreator.deleteObjects();
            page.acceptAlertOnPage();
        }
    }

    @Test
    public void test_delete_view() {

        //when:
        graphicalViewsPageSubject
                .openViewEditor(criteria.getIdentifier())
                .delete();

        //then:
        String body = graphicalViewsPageSubject.getBodyText();

        assertThat(body, not(containsString(viewName.getValue())));
    }
}