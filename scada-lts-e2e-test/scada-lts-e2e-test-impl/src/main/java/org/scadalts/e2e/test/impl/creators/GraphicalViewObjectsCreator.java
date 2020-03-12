package org.scadalts.e2e.test.impl.creators;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.GraphicalViewCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

import java.io.File;

import static org.scadalts.e2e.common.utils.FileUtil.getFileFromJar;

@Log4j2
public class GraphicalViewObjectsCreator implements CreatorObject<GraphicalViewsPage, GraphicalViewsPage> {

    private final NavigationPage navigationPage;
    private final GraphicalViewCriteria[] graphicalViewCriterias;
    private final static File BACKGROUND_FILE = _getBackgroundFile("image/background-test.png");
    private final static File BACKGROUND_SMALL_FILE = _getBackgroundFile("image/background-small-test.png");

    @Getter
    private GraphicalViewsPage graphicalViewsPage;

    public GraphicalViewObjectsCreator(NavigationPage navigationPage, GraphicalViewCriteria... graphicalViewCriterias) {
        this.navigationPage = navigationPage;
        this.graphicalViewCriterias = graphicalViewCriterias;
    }

    @Override
    public GraphicalViewsPage deleteObjects() {
        GraphicalViewsPage page = openPage();
        for (GraphicalViewCriteria criteria : graphicalViewCriterias) {
            if(page.containsObject(criteria)) {
                logger.info("delete object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                page.openViewEditor(criteria)
                        .delete()
                        .reopen();
            }
        }
        return page;
    }

    @Override
    public GraphicalViewsPage createObjects() {
        GraphicalViewsPage page = openPage();
        for (GraphicalViewCriteria criteria : graphicalViewCriterias) {
            if (!page.containsObject(criteria)) {
                logger.info("create object: {}, type: {}, xid: {}", criteria.getIdentifier().getValue(),
                        criteria.getType(), criteria.getXid().getValue());
                page.openViewCreator()
                        .chooseFile(BACKGROUND_FILE)
                        .uploadFile()
                        .setViewName(criteria.getIdentifier())
                        .selectComponentByName("Alarms List")
                        .addViewComponent()
                        .dragAndDropViewComponent()
                        .save();
            }
        }
        return page;
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

    public static File getBackgroundSmallFile() {
        return BACKGROUND_SMALL_FILE;
    }

    public static GraphicalViewsPage deleteAllGraphicalViewsTest(NavigationPage navigationPage) {
        GraphicalViewsPage graphicalViewsPage = navigationPage.openGraphicalViews();
        GraphicalViewCriteria graphicalViewCriteria = GraphicalViewCriteria.criteria(new GraphicalViewIdentifier("view_test"));
        while(graphicalViewsPage.containsObject(graphicalViewCriteria)) {
            graphicalViewsPage.openViewEditorFirst(graphicalViewCriteria)
                    .delete();
            graphicalViewsPage.reopen();
        }
        return graphicalViewsPage;
    }

    private static File _getBackgroundFile(String relativePath) {
        try {
            return getFileFromJar(relativePath);
        } catch (Throwable throwable) {
            logger.error(throwable.getMessage(),throwable);
            return new File("Not_found");
        }
    }
}
