package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.pages.graphicalviews.EditViewPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.core.utils.TestObjectsUtilible;

import java.io.File;
import java.util.Objects;

import static org.scadalts.e2e.common.utils.FileUtil.getFileFromJar;

@Log4j2
public class GraphicalViewTestObjectsUtil implements TestObjectsUtilible<GraphicalViewsPage, EditViewPage> {

    private final NavigationPage navigationPage;
    private final GraphicalViewCriteria criteria;
    private final static File BACKGROUND_FILE = _getBackgroundFile();

    @Getter
    private GraphicalViewsPage graphicalViewsPage;

    public GraphicalViewTestObjectsUtil(NavigationPage navigationPage, GraphicalViewCriteria criteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.navigationPage = navigationPage;
        this.criteria = criteria;
    }

    @Override
    public GraphicalViewsPage deleteObjects() {
        GraphicalViewsPage page = openPage();
        if(page.containsObject(criteria))
            page.openViewEditor(criteria)
                    .delete();
        return page;
    }

    @Override
    public EditViewPage createObjects() {
        return openPage().openViewCreator()
                .chooseFile(BACKGROUND_FILE)
                .uploadFile()
                .setViewName(criteria.getIdentifier())
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();
    }

    @Override
    public GraphicalViewsPage openPage() {
        if(graphicalViewsPage == null) {
            graphicalViewsPage = navigationPage.openGraphicalViews();
            return graphicalViewsPage;
        }
        return graphicalViewsPage.reopen();
    }

    public static File getBackgroundFile() {
        return BACKGROUND_FILE;
    }

    private static File _getBackgroundFile() {
        try {
            return getFileFromJar("image/background-test.png");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            return new File("Not_found");
        }
    }
}
