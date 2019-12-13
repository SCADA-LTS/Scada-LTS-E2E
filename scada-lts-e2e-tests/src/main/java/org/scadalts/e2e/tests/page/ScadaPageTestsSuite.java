package org.scadalts.e2e.tests.page;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.scadalts.e2e.tests.page.graphicalviews.CreateGraphicalViewTest;
import org.scadalts.e2e.tests.page.graphicalviews.DeleteGraphicalViewTest;
import org.scadalts.e2e.tests.page.graphicalviews.MultiTabGraphicalViewTest;
import org.scadalts.e2e.tests.page.main.MainPageTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        MainPageTest.class,
        CreateGraphicalViewTest.class,
        DeleteGraphicalViewTest.class,
        MultiTabGraphicalViewTest.class
})
public class ScadaPageTestsSuite {

}
