package org.scadalts.e2e.page.core.criterias;

import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.Xid;

import java.text.MessageFormat;

@ToString
public class Script {

    private final String content;
    private final Object[] values;
    private final static Script EMPTY = new Script("", Xid.xidEmpty(), "");

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
