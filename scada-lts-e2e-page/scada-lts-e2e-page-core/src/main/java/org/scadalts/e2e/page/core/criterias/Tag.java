package org.scadalts.e2e.page.core.criterias;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Tag {
    private final String value;

    private Tag(String value) {
        this.value = value;
    }

    public static Tag td() {
        return new Tag("td");
    }

    public static Tag tbody() {
        return new Tag("tbody");
    }

    public static Tag input() {
        return new Tag("input");
    }

    public static Tag tr() {
        return new Tag("tr");
    }

    public static Tag span() {
        return new Tag("span");
    }

    public static Tag each() {
        return new Tag("*");
    }

    public static Tag div() {
        return new Tag("div");
    }

    public static Tag li() {
        return new Tag("li");
    }

    public static Tag img() {
        return new Tag("img");
    }

    public static Tag table() {
        return new Tag("table");
    }

    public static Tag optgroup() {
        return new Tag("optgroup");
    }

    public static Tag option() {
        return new Tag("option");
    }

    @Override
    public String toString() {
        return value;
    }
}
