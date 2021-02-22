package org.scadalts.e2e.page.impl.pages.datasource.datapoint;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.scadalts.e2e.common.core.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.pages.HumanSimulation;
import org.scadalts.e2e.page.core.pages.PageObject;

import static com.codeborne.selenide.Selenide.$;

public class JavaBeanUtil {

    private static final HumanSimulation<?> human = new HumanSimulation(){
        @Override
        public PageObject getPage() {
            return null;
        }
    };

    public static String getSelected(By by) {
        return getSelected($(by));
    }

    public static String getSelected(SelenideElement element) {
        human.delay();
        return element.getSelectedText();
    }

    public static void selectOption(By by,
                                    DictionaryObject dictionaryObject) {
        selectOption($(by), dictionaryObject);
    }

    public static void selectOption(SelenideElement element,
                                      DictionaryObject dictionaryObject) {
        human.delay();
        element.selectOption(dictionaryObject.getName());
    }

    public static void setValue(By by, String value) {
        setValue($(by), value);
    }

    public static void setValue(SelenideElement element, String value) {
        human.delay();
        element.setValue(value);
    }

    public static void click(By by, boolean is) {
        click($(by), is);
    }

    public static void click(SelenideElement element, boolean is) {
        human.delay();
        if(isChecked(element) != is)
            element.click();
    }

    public static String getValue(By by) {
        SelenideElement element = $(by);
        return getValue(element);
    }

    public static boolean isChecked(By by) {
        SelenideElement element = $(by);
        return isChecked(element);
    }

    public static String getValue(SelenideElement element) {
        human.delay();
        return element.getValue();
    }

    public static boolean isChecked(SelenideElement element) {
        human.delay();
        return Boolean.parseBoolean(element.getAttribute("selected"));
    }
}
