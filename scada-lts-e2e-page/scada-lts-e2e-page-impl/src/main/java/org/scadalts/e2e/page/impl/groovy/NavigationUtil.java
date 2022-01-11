package org.scadalts.e2e.page.impl.groovy;


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


public class NavigationUtil {

    private static NavigationPage navigationPage;

    public static void init(NavigationPage navigation) {
        navigationPage = navigation;
    }

    public static GraphicalViewsPage openGraphicalViews() {
        return navigationPage.openGraphicalViews();
    }

    public static WatchListPage openWatchList() {
        return navigationPage.openWatchList();
    }

    public static PendingAlarmsPage openPendingAlarms() {
        return navigationPage.openPendingAlarms();
    }

    public static ReportsPage openReports() {
        return navigationPage.openReports();
    }

    public static EventHandlersPage openEventHandlers() {
        return navigationPage.openEventHandlers();
    }

    public static DataSourcesPage openDataSources() {
        return navigationPage.openDataSources();
    }

    public static ScheduledEventsPage openScheduledEvents() {
        return navigationPage.openScheduledEvents();
    }

    public static CompoundEventDetectorsPage openCompoundEventDetectors() {
        return navigationPage.openCompoundEventDetectors();
    }

    public static PointLinksPage openPointLinks() {
        return navigationPage.openPointLinks();
    }

    public static ScriptsPage openScripts() {
        return navigationPage.openScripts();
    }

    public static UsersPage openUsers() {
        return navigationPage.openUsers();
    }

    public static UserProfilesPage openUsersProfiles() {
        return navigationPage.openUsersProfiles();
    }

    public static PointHierarchyPage openPointHierarchy() {
        return navigationPage.openPointHierarchy();
    }

    public static MailingListsPage openMailingLists() {
        return navigationPage.openMailingLists();
    }

    public static PublishersPage openPublishers() {
        return navigationPage.openPublishers();
    }

    public static MaintenanceEventsPage openMaintenanceEvents() {
        return navigationPage.openMaintenanceEvents();
    }

    public static SystemInformationPage openSystemInformation() {
        return navigationPage.openSystemInformation();
    }

    public static ExportImportPage openExportImport() {
        return navigationPage.openExportImport();
    }

    public static SqlPage openSql() {
        return navigationPage.openSql();
    }

    public static HelpPage openHelp() {
        return navigationPage.openHelp();
    }

    public static NavigationPage openMain() {
        return NavigationPage.openPage();
    }
}
