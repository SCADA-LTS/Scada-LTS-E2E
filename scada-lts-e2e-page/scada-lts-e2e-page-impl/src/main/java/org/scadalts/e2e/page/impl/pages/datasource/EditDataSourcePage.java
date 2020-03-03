package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

import static javax.xml.bind.DatatypeConverter.parseInt;
import static org.scadalts.e2e.page.core.utils.E2eUtil.acceptAlert;

public class EditDataSourcePage extends PageObjectAbstract<EditDataSourcePage> {

    @FindBy(id = "dataSourceName")
    private SelenideElement dataSourceName;

    @FindBy(id = "dataSourceXid")
    private SelenideElement dataSourceXid;

    @FindBy(id = "updatePeriods")
    private SelenideElement updatePeriods;

    @FindBy(id = "updatePeriodType")
    private SelenideElement updatePeriodType;

    @FindBy(css = "img[onclick='saveDataSource()']")
    private SelenideElement saveDataSource;

    public static final String TITLE = "";

    EditDataSourcePage() {
        super(TITLE);
    }

    EditDataSourcePage(String title) {
        super(title);
    }

    @Override
    public EditDataSourcePage getPage() {
        return this;
    }

    public EditDataSourcePage setDataSourceName(DataSourceIdentifier dataSourceName) {
        this.dataSourceName.clear();
        this.dataSourceName.sendKeys(dataSourceName.getValue());
        return this;
    }

    public EditDataSourcePage setDataSourceXid(Xid dataSourceXid) {
        this.dataSourceXid.clear();
        this.dataSourceXid.sendKeys(dataSourceXid.getValue());
        return this;
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        this.updatePeriods.clear();
        String value = String.valueOf(updatePeriods);
        this.updatePeriods.sendKeys(value);
        return this;
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType componentName) {
        updatePeriodType.selectOption(componentName.getName());
        return this;
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType componentName) {
        updatePeriodType.selectOption(componentName.getName());
        return updatePeriodType.getValue();
    }

    public EditDataSourceWithPointListPage saveDataSource() {
        saveDataSource.click();
        acceptAlert();
        return Selenide.page(EditDataSourceWithPointListPage.class);
    }

    public String getDataSourceName() {
        return dataSourceName.getValue();
    }

    public String getDataSourceXid() {
        return dataSourceXid.getValue();
    }

    public int getUpdatePeriods() {
        return parseInt(updatePeriods.getValue());
    }

    public UpdatePeriodType getUpdatePeriodType() {
        return UpdatePeriodType.getType(updatePeriodType.getSelectedText());
    }

}
