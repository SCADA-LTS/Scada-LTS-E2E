package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.pages.graphicalviews.EditViewPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.io.File;
import java.util.Objects;

import static org.scadalts.e2e.common.utils.FileUtil.getFileFromJar;

@Log4j2
public class GraphicalViewTestsUtil {

    private final GraphicalViewsPage graphicalViewsPage;
    private final String viewName;
    private static File BACKGROUND_FILE = _getBackgroundFile();

    public GraphicalViewTestsUtil(NavigationPage navigationPage, String viewName) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.graphicalViewsPage = navigationPage.openGraphicalViews();
        this.viewName = viewName;
    }

    public File getBackgroundFile() {
        return BACKGROUND_FILE;
    }

    public void clean() {
        GraphicalViewsPage page = graphicalViewsPage.reopen();
        if(page.containsText(viewName))
            page.openViewEditor(viewName)
                .delete();
    }

    public EditViewPage addView() {
        return graphicalViewsPage.reopen().openViewCreator()
                .chooseFile(BACKGROUND_FILE)
                .uploadFile()
                .setViewName(viewName)
                .selectComponentByName("Alarms List")
                .addViewComponent()
                .dragAndDropViewComponent()
                .save();
    }

    public GraphicalViewsPage openGraphicalViews() {
        return graphicalViewsPage.reopen();
    }

    private static File _getBackgroundFile() {
        try {
            return getFileFromJar("background-test.png");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            return new File("");
        }
    }
}
