package org.scadalts.e2e.test.impl.backstop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.log4j.Log4j2;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.page.impl.criterias.identifiers.GraphicalViewIdentifier;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
@Log4j2
public class GenerateBackstopConfigJson {

    @Parameterized.Parameters(name = "{index}: id: {0}, viewName: {1}")
    public static List<String[]> data() {
        graphicalViewsPage = TestWithPageUtil.openNavigationPage()
                .openGraphicalViews();
        return graphicalViewsPage.getDataAllViews()
                .entrySet()
                .stream()
                .map(a -> new String[] {a.getKey(), a.getValue()})
                .collect(Collectors.toList());
    }

    private static GraphicalViewsPage graphicalViewsPage;
    private final String id;
    private final GraphicalViewIdentifier viewName;

    private static BackstopConfig backstopConfig;

    public GenerateBackstopConfigJson(String id, String viewName) {
        this.id = id;
        this.viewName = new GraphicalViewIdentifier(viewName);
    }

    @BeforeClass
    public static void setup() {
        graphicalViewsPage.reopen();
        backstopConfig = new BackstopConfig();
    }

    @AfterClass
    public static void close() {
        if(graphicalViewsPage != null)
            graphicalViewsPage.acceptAlertOnPage();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            objectMapper.writeValue(new File("scenarios.json"), backstopConfig);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
    }

    @Test
    public void test_click_all_graphical_view() {

       graphicalViewsPage.selectViewBy(viewName)
                .waitOnLoadedBackground()
                .printLoadingMeasure(MessageFormat.format("view: id: {0}, name: {1}", id, viewName.getValue()))
                .isSelectedView(viewName);

        String url = graphicalViewsPage.getCurrentUrl();
        backstopConfig.addScenario(new Scenario(viewName.getValue(), url));
    }
}
