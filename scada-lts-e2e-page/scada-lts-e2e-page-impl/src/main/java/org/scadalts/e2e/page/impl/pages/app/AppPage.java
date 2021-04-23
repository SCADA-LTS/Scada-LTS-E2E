package org.scadalts.e2e.page.impl.pages.app;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;
import org.scadalts.e2e.page.impl.pages.app.datapointlist.DataPointListPage;

public class AppPage extends MainPageObjectAbstract<AppPage> {

    @FindBy(css = "a[href='app.shtm']")
    private SelenideElement source;

    @FindBy(css = "a[href='./watch_list.shtm']")
    private SelenideElement main;

    @FindBy(css = "a[href*='#/alarms']")
    private SelenideElement alarms;

    @FindBy(css = "a[href*='#/historical-alarms']")
    private SelenideElement historicalAlarms;

    @FindBy(css = "a[href*='#/alarm-notifications']")
    private SelenideElement alarmNotifications;

    @FindBy(css = "a[href*='#/system-settings']")
    private SelenideElement systemSettings;

    public static final String TITLE = "";

    public AppPage() {
        super(TITLE, "/app.shtm");
    }

    @Override
    public E2eWebElement getSource() {
        if(main.is(Condition.visible)) {
            main.click();
        }
        return E2eWebElement.newInstance(source);
    }

    @Override
    public AppPage getPage() {
        return this;
    }

    public SelenideElement openOld() {
        return main;
    }

    public SelenideElement openAlarms() {
        return alarms;
    }

    public SelenideElement openHistoricalAlarms() {
        return historicalAlarms;
    }

    public SelenideElement openAlarmNotifications() {
        return alarmNotifications;
    }

    public DataPointListPage openDatapointList() {
        MainPageObjectAbstract<DataPointListPage> page = new DataPointListPage();
        return page.openPage();
    }

    public SelenideElement openSystemSettings() {
        return systemSettings;
    }
}
