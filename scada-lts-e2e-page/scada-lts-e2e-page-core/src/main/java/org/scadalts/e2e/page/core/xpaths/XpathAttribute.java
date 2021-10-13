package org.scadalts.e2e.page.core.xpaths;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
public final class XpathAttribute {

    private final String name;
    private final String value;

    private XpathAttribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static XpathAttribute onclick(String value) {
        return new XpathAttribute("@onclick", value);
    }

    public static XpathAttribute id(String value) {
        return new XpathAttribute("@id", value);
    }

    public static XpathAttribute clazz(String value) {
        return new XpathAttribute("@class", value);
    }

    public static XpathAttribute value(String value) {
        return new XpathAttribute("@value", value);
    }

    public static XpathAttribute text(String value) {
        return new XpathAttribute("text()", value);
    }

    public static XpathAttribute style(String value) {
        return new XpathAttribute("@style", value);
    }

    public static XpathAttribute empty() {
        return new XpathAttribute(".", "");
    }

    public static XpathAttribute selected() {
        return new XpathAttribute("@selected", "selected");
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(value);
    }
}
