package org.scadalts.e2e.page.impl.criterias;

import lombok.ToString;

import java.text.MessageFormat;

@ToString
public class Script {

    private final String content;
    private final Object[] values;
    private final static Script EMPTY = new Script("", Xid.xidEmpty(), VarCriteria.empty());

    public Script(String content, Object... values) {
        this.content = content;
        this.values = values;
    }

    public static Script empty() {
        return EMPTY;
    }

    public String getContent() {
        return MessageFormat.format(content, values);
    }
}
