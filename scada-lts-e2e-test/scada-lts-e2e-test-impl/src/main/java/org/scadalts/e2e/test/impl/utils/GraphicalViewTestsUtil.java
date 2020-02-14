package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;

import java.io.File;
import java.util.Objects;

import static org.scadalts.e2e.common.utils.FileUtil.getFile;

@Log4j2
public class GraphicalViewTestsUtil {

    private final GraphicalViewsPage graphicalViewsPage;
    private static File BACKGROUND_FILE;

    static {
        try {
            BACKGROUND_FILE = _getBackgroundFile();
        } catch (ConfigureTestException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public GraphicalViewTestsUtil(NavigationPage navigationPage) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException("Not logged in"));
        this.graphicalViewsPage = navigationPage.openGraphicalViews();
    }

    public File getBackgroundFile() {
        return BACKGROUND_FILE;
    }

    public GraphicalViewsPage openGraphicalViews() throws ConfigureTestException {
        return ExecutorUtil.execute(this::_openGraphicalViews, ConfigureTestException::new);
    }

    private GraphicalViewsPage _openGraphicalViews() {
        return graphicalViewsPage.reopen();
    }

    private static File _getBackgroundFile() throws ConfigureTestException {
        try {
            return getFile("background-test.png");
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            throw new ConfigureTestException(throwable);
        }
    }
}
