package org.scadalts.e2e.page.core.xpaths;

import lombok.EqualsAndHashCode;

import java.text.MessageFormat;

@EqualsAndHashCode
public final class XpathOperation {

    private final String operationPattern;
    private final Object[] args;

    private XpathOperation(String operationPattern, Object... args) {
        this.operationPattern = operationPattern;
        this.args = args;
    }

    public XpathOperation and(XpathOperation xpathOperation) {
        return new XpathOperation("{0} and {1}", this, xpathOperation);
    }

    public XpathOperation or(XpathOperation xpathOperation) {
        return new XpathOperation("{0} or {1}", this, xpathOperation);
    }

    public static XpathOperation contains(XpathAttribute xpathAttribute) {
        return new XpathOperation("contains({0},''{1}'')", xpathAttribute.getName().contains("text()") ? "." : xpathAttribute.getName(), xpathAttribute.getValue());
    }

    public static XpathOperation equal(XpathAttribute xpathAttribute) {
        return new XpathOperation("{0}=''{1}''", xpathAttribute.getName(), xpathAttribute.getValue());
    }

    public static XpathOperation positionMod(int sectionSize, int everyoneInPosition) {
        return new XpathOperation("position() mod {0} = {1}", sectionSize, everyoneInPosition);
    }

    @Override
    public String toString() {
        return MessageFormat.format(operationPattern, args);
    }

}
