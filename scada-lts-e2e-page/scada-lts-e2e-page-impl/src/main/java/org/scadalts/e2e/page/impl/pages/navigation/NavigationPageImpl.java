package org.scadalts.e2e.page.impl.pages.navigation;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.MainPageObject;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
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

import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;


class NavigationPageImpl extends PageObjectAbstract<NavigationPage> implements NavigationPage {

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

    @FindBy(css = ".userName")
    private SelenideElement userName;

    public NavigationPageImpl() {
        super("");
    }

    @Override
    public NavigationPage getPage() {
        return this;
    }

    @Override
    public void logout() {
        this.logout.click();
        acceptAlert();
    }

    @Override
    public GraphicalViewsPage openGraphicalViews() {
        MainPageObject<GraphicalViewsPage> page = new GraphicalViewsPage(graphicalViews);
        return page.openPage();
    }

    @Override
    public WatchListPage openWatchList() {
        MainPageObject<WatchListPage> page = new WatchListPage(watchList);
        return page.openPage();
    }

    @Override
    public PendingAlarmsPage openPendingAlarms() {
        MainPageObject<PendingAlarmsPage> page = new PendingAlarmsPage(pendingAlarms);
        return page.openPage();
    }

    @Override
    public ReportsPage openReports() {
        MainPageObject<ReportsPage> page = new ReportsPage(reports);
        return page.openPage();
    }

    @Override
    public EventHandlersPage openEventHandlers() {
        MainPageObject<EventHandlersPage> page = new EventHandlersPage(eventHandlers);
        return page.openPage();
    }

    @Override
    public DataSourcesPage openDataSources() {
        MainPageObject<DataSourcesPage> page = new DataSourcesPage(dataSources);
        return page.openPage();
    }

    @Override
    public ScheduledEventsPage openScheduledEvents() {
        MainPageObject<ScheduledEventsPage> page = new ScheduledEventsPage(scheduledEvents);
        return page.openPage();
    }

    @Override
    public CompoundEventDetectorsPage openCompoundEventDetectors() {
        MainPageObject<CompoundEventDetectorsPage> page = new CompoundEventDetectorsPage(compoundEventDetectors);
        return page.openPage();
    }

    @Override
    public PointLinksPage openPointLinks() {
        MainPageObject<PointLinksPage> page = new PointLinksPage(pointLinks);
        return page.openPage();
    }

    @Override
    public ScriptsPage openScripts() {
        MainPageObject<ScriptsPage> page = new ScriptsPage(scripts);
        return page.openPage();
    }

    @Override
    public UsersPage openUsers() {
        MainPageObject<UsersPage> page = new UsersPage(users);
        return page.openPage();
    }

    @Override
    public UserProfilesPage openUsersProfiles() {
        MainPageObject<UserProfilesPage> page = new UserProfilesPage(userProfiles);
        return page.openPage();
    }

    @Override
    public PointHierarchyPage openPointHierarchy() {
        MainPageObject<PointHierarchyPage> page = new PointHierarchyPage(pointHierarchy);
        return page.openPage();
    }

    @Override
    public MailingListsPage openMailingLists() {
        MainPageObject<MailingListsPage> page = new MailingListsPage(mailingLists);
        return page.openPage();
    }

    @Override
    public PublishersPage openPublishers() {
        MainPageObject<PublishersPage> page = new PublishersPage(publishers);
        return page.openPage();
    }

    @Override
    public MaintenanceEventsPage openMaintenanceEvents() {
        MainPageObject<MaintenanceEventsPage> page = new MaintenanceEventsPage(maintenanceEvents);
        return page.openPage();
    }

    @Override
    public SystemInformationPage openSystemInformation() {
        MainPageObject<SystemInformationPage> page = new SystemInformationPage(systemInformation);
        return page.openPage();
    }

    @Override
    public ExportImportPage openExportImport() {
        MainPageObject<ExportImportPage> page = new ExportImportPage(exportImport);
        return page.openPage();
    }

    @Override
    public SqlPage openSql() {
        MainPageObject<SqlPage> page = new SqlPage(sql);
        return page.openPage();
    }

    @Override
    public HelpPage openHelp() {
        MainPageObject<HelpPage> page = new HelpPage(help);
        return page.openPage();
    }

    @Override
    public String getUserName() {
        return userName.getText();
    }
}
