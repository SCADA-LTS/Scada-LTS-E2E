package org.scadalts.e2e.test.impl.tests.page.graphicalviews;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.creators.GraphicalViewObjectsCreator;
import org.scadalts.e2e.test.impl.runners.TestParameterizedWithPageRunner;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(TestParameterizedWithPageRunner.class)
public class CreateGraphicalViewPageTest {

    @Parameterized.Parameters(name = "{index}: {0} {1}")
    public static Object[][] data() {

        File backgroundSmall = GraphicalViewObjectsCreator.getBackgroundSmallFile();
        File background = GraphicalViewObjectsCreator.getBackgroundFile();

        return new Object[][] {
                {IdentifierObjectFactory.viewName(), background},
//                {IdentifierObjectFactory.viewName(), backgroundSmall}
        };
    }

    private final GraphicalViewIdentifier identifier;
    private final File background;

    public CreateGraphicalViewPageTest(GraphicalViewIdentifier identifier, File background) {
        this.identifier = identifier;
        this.background = background;
    }

    private GraphicalViewObjectsCreator graphicalViewObjectsCreator;
    private GraphicalViewsPage graphicalViewsPageSubject;

    @Before
    public void setup() {
        GraphicalViewCriteria criteria = GraphicalViewCriteria.criteria(identifier);
        graphicalViewObjectsCreator = new GraphicalViewObjectsCreator(TestWithPageUtil.getNavigationPage(), criteria);
        graphicalViewsPageSubject = graphicalViewObjectsCreator.openPage();
    }

    @After
    public void clean() {
        graphicalViewObjectsCreator.deleteObjects();
    }

    @Test
    public void test_create_view_with_background() {
        //when:
        graphicalViewsPageSubject.openViewCreator()
                .chooseFile(background)
                .uploadFile()
                .setViewName(identifier)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .dragAndDropViewComponent()
                .dragAndDropViewComponent()
                .save()
                .waitOnLoadedBackground();

        //then:
        String body = graphicalViewsPageSubject.getBodyText();

        assertThat(body, containsString(identifier.getValue()));
    }
}
