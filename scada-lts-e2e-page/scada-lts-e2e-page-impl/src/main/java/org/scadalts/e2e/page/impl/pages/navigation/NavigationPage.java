package org.scadalts.e2e.page.impl.pages.navigation;

import org.scadalts.e2e.page.core.pages.PageObject;
import org.scadalts.e2e.page.impl.pages.alarms.PendingAlarmsPage;
import org.scadalts.e2e.page.impl.pages.compoundeventdetectors.CompoundEventDetectorsPage;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.exportimport.ExportImportPage;
import org.scadalts.e2e.page.impl.pages.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.page.impl.pages.help.HelpPage;
import org.scadalts.e2e.page.impl.pages.mailinglists.MailingListsPage;
import org.scadalts.e2e.page.impl.pages.maintenanceevents.MaintenanceEventsPage;
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

import java.util.Set;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public interface NavigationPage extends PageObject<NavigationPage> {

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

    String getUserName();

    String URL_REF = "/watch_list.shtm";

    static NavigationPage openPage() {
        return open(URL_REF, NavigationPageImpl.class);
    }

    static Set<String> tabsOpened() {
        return getWebDriver().getWindowHandles();
    }
}
