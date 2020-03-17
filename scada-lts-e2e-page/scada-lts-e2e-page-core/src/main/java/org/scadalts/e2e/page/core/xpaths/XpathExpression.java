package org.scadalts.e2e.page.core.xpaths;

import org.scadalts.e2e.page.core.criterias.Tag;

import java.text.MessageFormat;

public final class XpathExpression {

    private final String expressionPattern;
    private final Object[] args;

    private XpathExpression(String expressionPattern, Tag tag, XpathOperation ops) {
        this.expressionPattern = expressionPattern;
        this.args = new Object[]{tag, ops};
    }

    private XpathExpression(String expressionPattern, Object... args) {
        this.expressionPattern = expressionPattern;
        this.args = args;
    }

    public static XpathExpression xpath(Tag tag, XpathOperation ops) {
        return new XpathExpression("//{0}[{1}]", tag, ops);
    }

    public static XpathExpression xpath(Tag tag, XpathExpression exp) {
        return new XpathExpression("//{0}[{1}]", tag, exp.expression());
    }

    public XpathExpression add(XpathExpression xpathExpression) {
        return new XpathExpression("{0}{1}", this, xpathExpression);
    }

    public String expression() {
        return MessageFormat.format(".{0}", this);
    }

    @Override
    public String toString() {
        return MessageFormat.format(expressionPattern, args);
    }
}
