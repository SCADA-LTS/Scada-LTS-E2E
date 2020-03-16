package org.scadalts.e2e.page.impl.pages.datasource;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.page.core.pages.PageObjectAbstract;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

import static javax.xml.bind.DatatypeConverter.parseInt;
import static org.scadalts.e2e.page.core.utils.AlertUtil.acceptAlertAfterClick;

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


    public EditDataSourcePage setDataSourceName(DataSourceIdentifier dataSourceIdentifier) {
        delay();
        this.dataSourceName.clear();
        this.dataSourceName.setValue(dataSourceIdentifier.getValue());
        return this;
    }

    public EditDataSourcePage setDataSourceXid(Xid dataSourceXid) {
        delay();
        this.dataSourceXid.clear();
        this.dataSourceXid.setValue(dataSourceXid.getValue());
        return this;
    }

    public EditDataSourcePage setUpdatePeriods(int updatePeriods) {
        delay();
        this.updatePeriods.clear();
        String value = String.valueOf(updatePeriods);
        this.updatePeriods.setValue(value);
        return this;
    }

    public EditDataSourcePage selectUpdatePeriodType(UpdatePeriodType updatePeriodType) {
        delay();
        this.updatePeriodType.selectOption(updatePeriodType.getName());
        return this;
    }

    public String selectUpdatePeriodTypeValue(UpdatePeriodType updatePeriodType) {
        delay();
        this.updatePeriodType.selectOption(updatePeriodType.getName());
        return this.updatePeriodType.getValue();
    }

    public EditDataSourceWithPointListPage saveDataSource() {
        delay();
        acceptAlertAfterClick(saveDataSource);
        return Selenide.page(EditDataSourceWithPointListPage.class);
    }

    public String getDataSourceName() {
        delay();
        return dataSourceName.getValue();
    }

    public String getDataSourceXid() {
        delay();
        return dataSourceXid.getValue();
    }

    public int getUpdatePeriods() {
        delay();
        return parseInt(updatePeriods.getValue());
    }

    public UpdatePeriodType getUpdatePeriodType() {
        delay();
        return UpdatePeriodType.getType(updatePeriodType.getSelectedText());
    }

}
