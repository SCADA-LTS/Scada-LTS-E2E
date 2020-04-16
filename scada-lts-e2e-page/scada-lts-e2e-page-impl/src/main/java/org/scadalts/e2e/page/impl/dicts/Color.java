package org.scadalts.e2e.page.impl.dicts;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.dicts.DictionaryObject;

import java.util.stream.Stream;

@Getter
@Log4j2
public enum Color implements DictionaryObject {

    WHITE("rgb(255, 255, 255)", "#ffffff"),
    LIGHT_GREEN("rgb(0, 255, 0)", "#00ff00"),
    GREEN("rgb(0, 128, 0)", "#008000"),
    BLUE("rgb(0, 0, 255)", "#0000ff"),
    LIGHT_GRAY("rgb(192, 192, 192)", "#c0c0c0"),
    YELLOW("rgb(255, 255, 0)", "#ffff00"),
    MAGENTA("rgb(255, 0, 255)", "#ff00ff"),
    NAVY_BLUE("rgb(0, 0, 128)", "#000080"),
    GRAY("rgb(128, 128, 128)", "#808080"),
    RED("rgb(255, 0, 0)", "#ff0000"),
    FILLET("rgb(128, 0, 128)", "#800080"),
    BLACK("rgb(0, 0, 0)", "#000000");

    private final String id;
    private final String code;

    Color(String id, String code) {
        this.id = id;
        this.code = code;
    }

    @Override
    public String getName() {
        return this.name();
    }

    public static Color getType(String code) {
        return Stream.of(Color.values())
                .filter(a -> a.getCode().equalsIgnoreCase(code))
                .findFirst().orElse(WHITE);
    }
}
