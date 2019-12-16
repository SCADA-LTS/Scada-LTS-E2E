package org.scadalts.e2e.pages.util;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.scadalts.e2e.pages.dict.ScadaDictionary;

import java.text.MessageFormat;
import java.util.Objects;

public class FindInTable {

    public static SelenideElement findRow(String objectName, ScadaDictionary objectType,
                                     By spliteTable, SelenideElement table) {
        table.click();
        return table.findAll(spliteTable).stream()
                .filter(Objects::nonNull)
                .filter(a -> a.getText() != null)
                .filter(a -> a.getText().contains(objectType.getTypeName()))
                .filter(a -> a.getText().contains(objectName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException(_generateMsq(objectName, objectType, spliteTable, table)));
    }

    private static String _generateMsq(String objectName, ScadaDictionary objectType, By spliteTable, SelenideElement table) {
        return MessageFormat.format("objectName: {0}, objectTyoe: {1}, by: {2}, table: {3}",
                objectName, objectType, spliteTable, table.name());
    }
}
