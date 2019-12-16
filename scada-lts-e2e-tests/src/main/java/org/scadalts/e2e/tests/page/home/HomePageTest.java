package org.scadalts.e2e.tests.page.home;


import org.junit.Test;
import org.scadalts.e2e.tests.E2e;
import org.scadalts.e2e.pages.page.alarms.PendingAlarmsPage;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.help.HelpPage;
import org.scadalts.e2e.pages.page.home.HomePage;
import org.scadalts.e2e.pages.page.pointlinks.PointLinksPage;
import org.scadalts.e2e.pages.page.publishers.PublishersPage;
import org.scadalts.e2e.pages.page.reports.ReportsPage;
import org.scadalts.e2e.pages.page.scripts.ScriptsPage;
import org.scadalts.e2e.pages.page.sql.SqlPage;
import org.scadalts.e2e.pages.page.users.UsersPage;
import org.scadalts.e2e.pages.page.watchlist.WatchListPage;
import org.scadalts.e2e.pages.page.compoundeventdetectors.CompoundEventDetectorsPage;
import org.scadalts.e2e.pages.page.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.pages.page.exportimport.ExportImportPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.pages.page.mailinglists.MailingListsPage;
import org.scadalts.e2e.pages.page.maintenanceevents.MaintenanceEventsPage;
import org.scadalts.e2e.pages.page.pointhierarchy.PointHierarchyPage;
import org.scadalts.e2e.pages.page.scheduledevents.ScheduledEventsPage;
import org.scadalts.e2e.pages.page.systemsettings.SystemInformationPage;
import org.scadalts.e2e.pages.page.userprofiles.UserProfilesPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class HomePageTest extends E2e {

    private HomePage subjectPage = getHomePage();

    @Test
    public void test_openViewGraphics() {
        //when:
        GraphicalViewsPage page = subjectPage.openGraphicalViews();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Graphic views"));
    }

    @Test
    public void test_openAlarms() {
        //when:
        PendingAlarmsPage page = subjectPage.openPendingAlarms();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Pending alarms"));
    }

    @Test
    public void test_openMailingLists() {
        //when:
        MailingListsPage page = subjectPage.openMailingLists();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Mailing lists"));
    }

    @Test
    public void test_openMaintenanceEvents() {
        //when:
        MaintenanceEventsPage page = subjectPage.openMaintenanceEvents();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Maintenance events"));
    }

    @Test
    public void test_openPointHierarchy() {
        //when:
        PointHierarchyPage page = subjectPage.openPointHierarchy();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Point Hierarchy"));
    }

    @Test
    public void test_openPointLinks() {
        //when:
        PointLinksPage page = subjectPage.openPointLinks();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Point links"));
    }

    @Test
    public void test_openPublishers() {
        //when:
        PublishersPage page = subjectPage.openPublishers();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Publishers"));
    }

    @Test
    public void test_openReports() {
        //when:
        ReportsPage page = subjectPage.openReports();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Report"));
    }

    @Test
    public void test_openScheduledEvents(){
        //when:
        ScheduledEventsPage page = subjectPage.openScheduledEvents();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Scheduled events"));
    }

    @Test
    public void test_openScripts() {
        //when:
        ScriptsPage page = subjectPage.openScripts();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Scripts"));
    }

    @Test
    public void test_openSql() {
        //when:
        SqlPage page = subjectPage.openSql();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("SQL"));
    }

    @Test
    public void test_openSystemSettings() {
        //when:
        SystemInformationPage page = subjectPage.openSystemInformation();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("System information"));
    }

    @Test
    public void test_openUsers() {
        //when:
        UsersPage page = subjectPage.openUsers();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Users"));
    }

    @Test
    public void test_openUsersProfiles(){
        //when:
        UserProfilesPage page = subjectPage.openUsersProfiles();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Manage user profiles"));
    }

    @Test
    public void test_openWatchList() {
        //when:
        WatchListPage page = subjectPage.openWatchList();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Watch list"));
    }

    @Test
    public void test_openHelp() {
        //when:
        HelpPage page = subjectPage.openHelp();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Help"));
    }

    @Test
    public void test_openExportImport() {
        //when:
        ExportImportPage page = subjectPage.openExportImport();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Export"));
    }

    @Test
    public void test_openEventHandlers() {
        //when:
        EventHandlersPage page = subjectPage.openEventHandlers();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Event handlers"));
    }

    @Test
    public void test_openDataSources() {
        //when:
        DataSourcesPage page = subjectPage.openDataSources();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Data sources"));
    }

    @Test
    public void test_openCompoundEventDetectors() {
        //when:
        CompoundEventDetectorsPage page = subjectPage.openCompoundEventDetectors();
        //then:
        String body = page.getBodyText();
        assertThat(body, containsString("Compound event detectors"));
    }
}