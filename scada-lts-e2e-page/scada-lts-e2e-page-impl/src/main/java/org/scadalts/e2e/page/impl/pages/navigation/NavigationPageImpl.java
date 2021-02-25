package org.scadalts.e2e.page.impl.pages.navigation;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j2;
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

import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAfterClick;

@Log4j2
class NavigationPageImpl extends PageObjectAbstract<NavigationPage> implements NavigationPage {

    @FindBy(css = "a[href='logout.htm']")
    private SelenideElement logout;

    @FindBy(css = ".userName")
    private SelenideElement userName;

    public NavigationPageImpl() {
        super();
    }

    @Override
    public NavigationPage getPage() {
        return this;
    }

    @Override
    public void logout() {
        acceptAfterClick(logout);
    }

    @Override
    public GraphicalViewsPage openGraphicalViews() {
        MainPageObject<GraphicalViewsPage> page = new GraphicalViewsPage();
        return page.openPage();
    }

    @Override
    public WatchListPage openWatchList() {
        MainPageObject<WatchListPage> page = new WatchListPage();
        return page.openPage();
    }

    @Override
    public PendingAlarmsPage openPendingAlarms() {
        MainPageObject<PendingAlarmsPage> page = new PendingAlarmsPage();
        return page.openPage();
    }

    @Override
    public ReportsPage openReports() {
        MainPageObject<ReportsPage> page = new ReportsPage();
        return page.openPage();
    }

    @Override
    public EventHandlersPage openEventHandlers() {
        MainPageObject<EventHandlersPage> page = new EventHandlersPage();
        return page.openPage();
    }

    @Override
    public DataSourcesPage openDataSources() {
        MainPageObject<DataSourcesPage> page = new DataSourcesPage();
        return page.openPage();
    }

    @Override
    public ScheduledEventsPage openScheduledEvents() {
        MainPageObject<ScheduledEventsPage> page = new ScheduledEventsPage();
        return page.openPage();
    }

    @Override
    public CompoundEventDetectorsPage openCompoundEventDetectors() {
        MainPageObject<CompoundEventDetectorsPage> page = new CompoundEventDetectorsPage();
        return page.openPage();
    }

    @Override
    public PointLinksPage openPointLinks() {
        MainPageObject<PointLinksPage> page = new PointLinksPage();
        return page.openPage();
    }

    @Override
    public ScriptsPage openScripts() {
        MainPageObject<ScriptsPage> page = new ScriptsPage();
        return page.openPage();
    }

    @Override
    public UsersPage openUsers() {
        MainPageObject<UsersPage> page = new UsersPage();
        return page.openPage();
    }

    @Override
    public UserProfilesPage openUsersProfiles() {
        MainPageObject<UserProfilesPage> page = new UserProfilesPage();
        return page.openPage();
    }

    @Override
    public PointHierarchyPage openPointHierarchy() {
        MainPageObject<PointHierarchyPage> page = new PointHierarchyPage();
        return page.openPage();
    }

    @Override
    public MailingListsPage openMailingLists() {
        MainPageObject<MailingListsPage> page = new MailingListsPage();
        return page.openPage();
    }

    @Override
    public PublishersPage openPublishers() {
        MainPageObject<PublishersPage> page = new PublishersPage();
        return page.openPage();
    }

    @Override
    public MaintenanceEventsPage openMaintenanceEvents() {
        MainPageObject<MaintenanceEventsPage> page = new MaintenanceEventsPage();
        return page.openPage();
    }

    @Override
    public SystemInformationPage openSystemInformation() {
        MainPageObject<SystemInformationPage> page = new SystemInformationPage();
        return page.openPage();
    }

    @Override
    public ExportImportPage openExportImport() {
        MainPageObject<ExportImportPage> page = new ExportImportPage();
        return page.openPage();
    }

    @Override
    public SqlPage openSql() {
        MainPageObject<SqlPage> page = new SqlPage();
        return page.openPage();
    }

    @Override
    public HelpPage openHelp() {
        MainPageObject<HelpPage> page = new HelpPage();
        return page.openPage();
    }

    @Override
    public String getUserName() {
        return userName.getText();
    }

}
