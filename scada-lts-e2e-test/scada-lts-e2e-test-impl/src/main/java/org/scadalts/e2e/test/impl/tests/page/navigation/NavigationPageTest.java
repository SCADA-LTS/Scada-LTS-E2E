package org.scadalts.e2e.test.impl.tests.page.navigation;


import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.common.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.pages.alarms.PendingAlarmsPage;
import org.scadalts.e2e.page.impl.pages.compoundeventdetectors.CompoundEventDetectorsPage;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.exportimport.ExportImportPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.help.HelpPage;
import org.scadalts.e2e.page.impl.pages.mailinglists.MailingListsPage;
import org.scadalts.e2e.page.impl.pages.maintenanceevents.MaintenanceEventsPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.pointhierarchy.PointHierarchyPage;
import org.scadalts.e2e.page.impl.pages.pointlinks.PointLinksPage;
import org.scadalts.e2e.page.impl.pages.publishers.PublishersPage;
import org.scadalts.e2e.page.impl.pages.reports.ReportsPage;
import org.scadalts.e2e.page.impl.pages.scheduledevents.ScheduledEventsPage;
import org.scadalts.e2e.page.impl.pages.scripts.ScriptsPage;
import org.scadalts.e2e.page.impl.pages.sql.SqlPage;
import org.scadalts.e2e.page.impl.pages.systemsettings.SystemInformationPage;
import org.scadalts.e2e.page.impl.pages.userprofiles.UserProfilesPage;
import org.scadalts.e2e.page.impl.pages.users.UsersPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(TestWithPageRunner.class)
public class NavigationPageTest {

    private static NavigationPage pageSubject;

    @BeforeClass
    public static void setup(){
        pageSubject = ExecutorUtil.executeSupplier(NavigationPage::openPage, ConfigureTestException::new);
    }

    @Test
    public void test_openViewGraphics() {

        //when:
        String body = pageSubject.openGraphicalViews()
                .waitOnLoadedBackground()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(GraphicalViewsPage.TITLE));
    }

    @Test
    public void test_openAlarms() {

        //when:
        String body = pageSubject.openPendingAlarms()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PendingAlarmsPage.TITLE));
    }

    @Test
    public void test_openMailingLists() {

        //when:
        String body = pageSubject.openMailingLists()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(MailingListsPage.TITLE));
    }

    @Test
    public void test_openMaintenanceEvents() {

        //when:
        String body = pageSubject.openMaintenanceEvents()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(MaintenanceEventsPage.TITLE));
    }

    @Test
    public void test_openPointHierarchy() {

        //when:
        String body = pageSubject.openPointHierarchy()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PointHierarchyPage.TITLE));
    }

    @Test
    public void test_openPointLinks() {

        //when:
        String body = pageSubject.openPointLinks()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PointLinksPage.TITLE));
    }

    @Test
    public void test_openPublishers() {

        //when:
        String body = pageSubject.openPublishers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PublishersPage.TITLE));
    }

    @Test
    public void test_openReports() {

        //when:
        String body = pageSubject.openReports()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ReportsPage.TITLE));
    }

    @Test
    public void test_openScheduledEvents(){

        //when:
        String body = pageSubject.openScheduledEvents()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ScheduledEventsPage.TITLE));
    }

    @Test
    public void test_openScripts() {

        //when:
        String body = pageSubject.openScripts()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ScriptsPage.TITLE));
    }

    @Test
    public void test_openSql() {

        //when:
        String body = pageSubject.openSql()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(SqlPage.TITLE));
    }

    @Test
    public void test_openSystemInformation() {

        //when:
        String body = pageSubject.openSystemInformation()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(SystemInformationPage.TITLE));
    }

    @Test
    public void test_openUsers() {

        //when:
        String body = pageSubject.openUsers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(UsersPage.TITLE));
    }

    @Test
    public void test_openUsersProfiles(){

        //when:
        String body = pageSubject.openUsersProfiles()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(UserProfilesPage.TITLE));
    }

    @Test
    public void test_openWatchList() {

        //when:
        String body = pageSubject.openWatchList()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(WatchListPage.TITLE));
    }

    @Test
    public void test_openHelp() {

        //when:
        String body = pageSubject.openHelp()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(HelpPage.TITLE));
    }

    @Test
    public void test_openExportImport() {

        //when:
        String body = pageSubject.openExportImport()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ExportImportPage.TITLE));
    }

    @Test
    public void test_openEventHandlers() {

        //when:
        String body = pageSubject.openEventHandlers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(EventHandlersPage.TITLE));
    }

    @Test
    public void test_openDataSources() {

        //when:
        String body = pageSubject.openDataSources()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(DataSourcesPage.TITLE));
    }

    @Test
    public void test_openCompoundEventDetectors() {

        //when:
        String body = pageSubject.openCompoundEventDetectors()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(CompoundEventDetectorsPage.TITLE));
    }
}