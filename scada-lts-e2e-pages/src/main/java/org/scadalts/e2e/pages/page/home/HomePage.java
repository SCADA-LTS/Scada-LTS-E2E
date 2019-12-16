package org.scadalts.e2e.pages.page.home;

import org.scadalts.e2e.pages.type.PageObject;
import org.scadalts.e2e.pages.page.help.HelpPage;
import org.scadalts.e2e.pages.page.sql.SqlPage;
import org.scadalts.e2e.pages.page.users.UsersPage;
import org.scadalts.e2e.pages.page.alarms.PendingAlarmsPage;
import org.scadalts.e2e.pages.page.compoundeventdetectors.CompoundEventDetectorsPage;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.pages.page.exportimport.ExportImportPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.pages.page.mailinglists.MailingListsPage;
import org.scadalts.e2e.pages.page.maintenanceevents.MaintenanceEventsPage;
import org.scadalts.e2e.pages.page.pointhierarchy.PointHierarchyPage;
import org.scadalts.e2e.pages.page.pointlinks.PointLinksPage;
import org.scadalts.e2e.pages.page.publishers.PublishersPage;
import org.scadalts.e2e.pages.page.reports.ReportsPage;
import org.scadalts.e2e.pages.page.scheduledevents.ScheduledEventsPage;
import org.scadalts.e2e.pages.page.scripts.ScriptsPage;
import org.scadalts.e2e.pages.page.systemsettings.SystemInformationPage;
import org.scadalts.e2e.pages.page.userprofiles.UserProfilesPage;
import org.scadalts.e2e.pages.page.watchlist.WatchListPage;

import java.util.Set;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public interface HomePage extends PageObject {

    GraphicalViewsPage openGraphicalViews();

    WatchListPage openWatchList();

    PendingAlarmsPage openPendingAlarms();

    ReportsPage openReports();

    EventHandlersPage openEventHandlers();

    DataSourcesPage openDataSources();

    ScheduledEventsPage openScheduledEvents();

    CompoundEventDetectorsPage openCompoundEventDetectors();

    PointLinksPage openPointLinks();

    ScriptsPage openScripts();

    UsersPage openUsers();

    UserProfilesPage openUsersProfiles();

    PointHierarchyPage openPointHierarchy();

    MailingListsPage openMailingLists();

    PublishersPage openPublishers();

    MaintenanceEventsPage openMaintenanceEvents();

    SystemInformationPage openSystemInformation();

    ExportImportPage openExportImport();

    SqlPage openSql();

    HelpPage openHelp();

    void logout();

    String URL_REF = "/watch_list.shtm";

    static HomePage openPage() {
        return open(URL_REF, HomePageImpl.class);
    }

    static Set<String> tabsOpened() {
        return getWebDriver().getWindowHandles();
    }
}
