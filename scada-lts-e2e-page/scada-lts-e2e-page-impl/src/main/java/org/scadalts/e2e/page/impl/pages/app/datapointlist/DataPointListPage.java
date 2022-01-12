package org.scadalts.e2e.page.impl.pages.app.datapointlist;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.components.E2eWebElement;
import org.scadalts.e2e.page.core.criterias.Tag;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;
import org.scadalts.e2e.page.core.pages.MainPageObjectAbstract;

import static com.codeborne.selenide.Condition.not;

public class DataPointListPage extends MainPageObjectAbstract<DataPointListPage> {

    @FindBy(css = "a[href*='#/datapoint-list']")
    private SelenideElement source;

   // private SelenideElement parent;

    @FindBy(css = "div[class*='after']")
    private SelenideElement next;

    public DataPointListPage() {
        super("", "/app.shtm#/datapoint-list");
       //this.parent = parent;
    }

    @Override
    public DataPointListPage getPage() {
        return this;
    }

    @Override
    public boolean containsObject(IdentifierObject identifier) {
        boolean contained = false;

        while(!contained && next.is(not(Condition.disabled))) {
            next.click();

            contained = super.containsObject(new IdentifierObject() {
                @Override
                public String getValue() {
                    return identifier.getValue();
                }

                @Override
                public NodeCriteria getNodeCriteria() {
                    return NodeCriteria.exactly(identifier, Tag.tr());
                }

                @Override
                public DictionaryObject getType() {
                    return identifier.getType();
                }
            });
        }
        return contained;
    }

    @Override
    public E2eWebElement getSource() {
        return E2eWebElement.newInstance(source);
    }
}
