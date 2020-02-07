package org.scadalts.e2e.test.impl.tests.page.navigation;


import org.junit.Test;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class NavigationPageTest {

    private static NavigationPage subjectPage = NavigationPage.openPage();

    @Test
    public void test_openViewGraphics() {

        //when:
        String body = subjectPage.openGraphicalViews()
                .waitOnLoadedBackground()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(GraphicalViewsPage.TITLE));
    }

    @Test
    public void test_openAlarms() {

        //when:
        String body = subjectPage.openPendingAlarms()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PendingAlarmsPage.TITLE));
    }

    @Test
    public void test_openMailingLists() {

        //when:
        String body = subjectPage.openMailingLists()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(MailingListsPage.TITLE));
    }

    @Test
    public void test_openMaintenanceEvents() {

        //when:
        String body = subjectPage.openMaintenanceEvents()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(MaintenanceEventsPage.TITLE));
    }

    @Test
    public void test_openPointHierarchy() {

        //when:
        String body = subjectPage.openPointHierarchy()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PointHierarchyPage.TITLE));
    }

    @Test
    public void test_openPointLinks() {

        //when:
        String body = subjectPage.openPointLinks()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PointLinksPage.TITLE));
    }

    @Test
    public void test_openPublishers() {

        //when:
        String body = subjectPage.openPublishers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(PublishersPage.TITLE));
    }

    @Test
    public void test_openReports() {

        //when:
        String body = subjectPage.openReports()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ReportsPage.TITLE));
    }

    @Test
    public void test_openScheduledEvents(){

        //when:
        String body = subjectPage.openScheduledEvents()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ScheduledEventsPage.TITLE));
    }

    @Test
    public void test_openScripts() {

        //when:
        String body = subjectPage.openScripts()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ScriptsPage.TITLE));
    }

    @Test
    public void test_openSql() {

        //when:
        String body = subjectPage.openSql()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(SqlPage.TITLE));
    }

    @Test
    public void test_openSystemInformation() {

        //when:
        String body = subjectPage.openSystemInformation()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(SystemInformationPage.TITLE));
    }

    @Test
    public void test_openUsers() {

        //when:
        String body = subjectPage.openUsers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(UsersPage.TITLE));
    }

    @Test
    public void test_openUsersProfiles(){

        //when:
        String body = subjectPage.openUsersProfiles()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(UserProfilesPage.TITLE));
    }

    @Test
    public void test_openWatchList() {

        //when:
        String body = subjectPage.openWatchList()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(WatchListPage.TITLE));
    }

    @Test
    public void test_openHelp() {

        //when:
        String body = subjectPage.openHelp()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(HelpPage.TITLE));
    }

    @Test
    public void test_openExportImport() {

        //when:
        String body = subjectPage.openExportImport()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(ExportImportPage.TITLE));
    }

    @Test
    public void test_openEventHandlers() {

        //when:
        String body = subjectPage.openEventHandlers()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(EventHandlersPage.TITLE));
    }

    @Test
    public void test_openDataSources() {

        //when:
        String body = subjectPage.openDataSources()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(DataSourcesPage.TITLE));
    }

    @Test
    public void test_openCompoundEventDetectors() {

        //when:
        String body = subjectPage.openCompoundEventDetectors()
                .printLoadingMeasure()
                .getBodyText();

        //then:
        assertThat(body, containsString(CompoundEventDetectorsPage.TITLE));
    }
}