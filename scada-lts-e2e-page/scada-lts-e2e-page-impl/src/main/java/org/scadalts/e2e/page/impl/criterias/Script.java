package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import lombok.ToString;

import java.text.MessageFormat;

@Data
@ToString
public class Script {

    private final String content;
    private final VarCriteria varName;
    private final static Script EMPTY = new Script("", VarCriteria.empty());

    public static Script empty() {
        return EMPTY;
    }

    public String getContent() {
        return MessageFormat.format(content, varName.getIdentifier().getValue());
    }
}
