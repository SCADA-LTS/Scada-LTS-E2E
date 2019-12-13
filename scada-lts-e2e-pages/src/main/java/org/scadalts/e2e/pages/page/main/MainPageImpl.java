package org.scadalts.e2e.pages.page.main;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.pages.page.sql.SqlPage;
import org.scadalts.e2e.pages.page.alarms.PendingAlarmsPage;

import org.scadalts.e2e.pages.page.compoundeventdetectors.CompoundEventDetectorsPage;
import org.scadalts.e2e.pages.page.datasource.DataSourcesPage;
import org.scadalts.e2e.pages.page.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.pages.page.exportimport.ExportImportPage;
import org.scadalts.e2e.pages.page.graphicalviews.GraphicalViewsPage;
import org.scadalts.e2e.pages.page.help.HelpPage;
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
import org.scadalts.e2e.pages.page.users.UsersPage;
import org.scadalts.e2e.pages.page.watchlist.WatchListPage;
import org.scadalts.e2e.pages.component.E2eUtils;

import static com.codeborne.selenide.Selenide.page;

class MainPageImpl implements MainPage {

    @FindBy(css = "a[href='logout.htm']")
    private SelenideElement logout;

    @FindBy(css = "a[href='watch_list.shtm']")
    private SelenideElement watchList;

    @FindBy(css = "a[href='views.shtm']")
    private SelenideElement graphicalViews;

    @FindBy(css = "a[href='events.shtm']")
    private SelenideElement pendingAlarms;

    @FindBy(css = "a[href='reports.shtm']")
    private SelenideElement reports;

    @FindBy(css = "a[href='event_handlers.shtm']")
    private SelenideElement eventHandlers;

    @FindBy(css = "a[href='data_sources.shtm']")
    private SelenideElement dataSources;

    @FindBy(css = "a[href='scheduled_events.shtm']")
    private SelenideElement scheduledEvents;

    @FindBy(css = "a[href='compound_events.shtm']")
    private SelenideElement compoundEventDetectors;

    @FindBy(css = "a[href='point_links.shtm']")
    private SelenideElement pointLinks;

    @FindBy(css = "a[href='scripting.shtm']")
    private SelenideElement scripts;

    @FindBy(css = "a[href='users.shtm']")
    private SelenideElement users;

    @FindBy(css = "a[href='usersProfiles.shtm']")
    private SelenideElement userProfiles;

    @FindBy(css = "a[href='pointHierarchySLTS']")
    private SelenideElement pointHierarchy;

    @FindBy(css = "a[href='mailing_lists.shtm']")
    private SelenideElement mailingLists;

    @FindBy(css = "a[href='publishers.shtm']")
    private SelenideElement publishers;

    @FindBy(css = "a[href='maintenance_events.shtm']")
    private SelenideElement maintenanceEvents;

    @FindBy(css = "a[href='system_settings.shtm']")
    private SelenideElement systemInformation;

    @FindBy(css = "a[href='emport.shtm']")
    private SelenideElement exportImport;

    @FindBy(css = "a[href='sql.shtm']")
    private SelenideElement sql;

    @FindBy(css = "a[href='help.shtm']")
    private SelenideElement help;

    @Override
    public void logout() {
        this.logout.click();
        E2eUtils.acceptAlert();
    }

    @Override
    public GraphicalViewsPage openGraphicalViews() {
        this.graphicalViews.click();
        return page(new GraphicalViewsPage(graphicalViews));
    }

    @Override
    public WatchListPage openWatchList() {
        this.watchList.click();
        return page(new WatchListPage(watchList));
    }

    @Override
    public PendingAlarmsPage openPendingAlarms() {
        this.pendingAlarms.click();
        return page(new PendingAlarmsPage(pendingAlarms));
    }

    @Override
    public ReportsPage openReports() {
        this.reports.click();
        return page(new ReportsPage(reports));
    }

    @Override
    public EventHandlersPage openEventHandlers() {
        this.eventHandlers.click();
        return page(new EventHandlersPage(eventHandlers));
    }

    @Override
    public DataSourcesPage openDataSources() {
        this.dataSources.click();
        return page(new DataSourcesPage(dataSources));
    }

    @Override
    public ScheduledEventsPage openScheduledEvents() {
        this.scheduledEvents.click();
        return page(new ScheduledEventsPage(scheduledEvents));
    }

    @Override
    public CompoundEventDetectorsPage openCompoundEventDetectors() {
        this.compoundEventDetectors.click();
        return page(new CompoundEventDetectorsPage(compoundEventDetectors));
    }

    @Override
    public PointLinksPage openPointLinks() {
        this.pointLinks.click();
        return page(new PointLinksPage(pointLinks));
    }

    @Override
    public ScriptsPage openScripts() {
        this.scripts.click();
        return page(new ScriptsPage(scripts));
    }

    @Override
    public UsersPage openUsers() {
        this.users.click();
        return page(new UsersPage(users));
    }

    @Override
    public UserProfilesPage openUsersProfiles() {
        this.userProfiles.click();
        return page(new UserProfilesPage(userProfiles));
    }

    @Override
    public PointHierarchyPage openPointHierarchy() {
        this.pointHierarchy.click();
        return page(new PointHierarchyPage(pointHierarchy));
    }

    @Override
    public MailingListsPage openMailingLists() {
        this.mailingLists.click();
        return page(new MailingListsPage(mailingLists));
    }

    @Override
    public PublishersPage openPublishers() {
        this.publishers.click();
        return page(new PublishersPage(publishers));
    }

    @Override
    public MaintenanceEventsPage openMaintenanceEvents() {
        this.maintenanceEvents.click();
        return page(new MaintenanceEventsPage(maintenanceEvents));
    }

    @Override
    public SystemInformationPage openSystemInformation() {
        this.systemInformation.click();
        return page(new SystemInformationPage(systemInformation));
    }

    @Override
    public ExportImportPage openExportImport() {
        this.exportImport.click();
        return page(new ExportImportPage(exportImport));
    }

    @Override
    public SqlPage openSql() {
        this.sql.click();
        return page(new SqlPage(sql));
    }

    @Override
    public HelpPage openHelp() {
        this.help.click();
        return page(new HelpPage(help));
    }

    @Override
    public void closeWindows() {
        Selenide.closeWindow();
    }
}
