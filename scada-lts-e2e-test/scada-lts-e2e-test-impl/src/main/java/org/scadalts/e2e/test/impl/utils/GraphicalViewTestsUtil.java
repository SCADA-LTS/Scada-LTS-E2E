package org.scadalts.e2e.test.impl.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

import java.io.File;

import static org.scadalts.e2e.common.util.FileUtil.getFile;

public abstract class GraphicalViewTestsUtil {

    private static Logger logger = LogManager.getLogger(GraphicalViewTestsUtil.class);
    private final static NavigationPage homePage = E2eAbstractRunnable.getNavigationPage();
    private final static GraphicalViewsPage graphicalViewsPage = homePage.openGraphicalViews();

    public static final File BACKGROUND_FILE = getBackgroundFile();

    private static File getBackgroundFile() {
        try {
            return getFile("background-test.png");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            return null;
        }
    }

    public static GraphicalViewsPage openGraphicalViews() {
        return graphicalViewsPage.reopen();
    }
}
