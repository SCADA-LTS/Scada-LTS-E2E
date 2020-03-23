package org.scadalts.e2e.page.core.criterias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.text.MessageFormat;

@ToString
@EqualsAndHashCode
public class Script {

    private final String content;
    private final Object[] values;
    private final static Script EMPTY = new Script("", "", "");

    private Script(String content, Object... values) {
        this.content = content;
        this.values = values;
    }

    public static Script script(String content, Object... values) {
        return new Script(content, values);
    }

    public static Script sourceValue() {
        return new Script("return source.value;");
    }

    public static Script sourceValueIncreasedOne() {
        return new Script("return source.value + 1;");
    }

    public static Script empty() {
        return EMPTY;
    }

    public String getContent() {
        return MessageFormat.format(content, values);
    }

    @Override
    public String toString() {
        return getContent();
    }
}
