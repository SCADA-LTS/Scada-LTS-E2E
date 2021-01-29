package org.scadalts.e2e.page.core.criterias;

import lombok.ToString;
import org.scadalts.e2e.page.core.javascripts.JavascriptProvider;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.UnaryOperator;

@ToString
public class Script implements JavascriptProvider {

    private final String content;
    private final Object[] values;
    private final UnaryOperator<String> function;
    private final static Script EMPTY = new Script("", UnaryOperator.identity());

    private Script(String content, UnaryOperator<String> function, Object... values) {
        this.content = content;
        this.function = function;
        this.values = values;
    }

    public static Script script(String content, Object... values) {
        return new Script(content, a -> a, values);
    }

    public static Script sourceValue() {
        return new Script("return source.value;", a -> a);
    }

    public static Script sourceValueIncreasedOne() {
        return new Script("return source.value + 1;", a -> new BigDecimal(a).add(BigDecimal.ONE).toString());
    }

    public static Script sourceValueIncreasedTwo() {
        return new Script("return source.value + 2;", a -> new BigDecimal(a).add(BigDecimal.valueOf(2)).toString());
    }

    public static Script sourceValueIncreased(int i) {
        return new Script("return source.value + {0};", a -> new BigDecimal(a).add(new BigDecimal(i)).toString(), i);
    }

    public static Script empty() {
        return EMPTY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Script)) return false;
        Script script = (Script) o;
        return Objects.equals(content, script.content) &&
                Arrays.equals(values, script.values);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(content);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }

    @Override
    public String toString() {
        return getScriptToExecute();
    }

    @Override
    public String executeInJava(String arg) {
        return function.apply(arg);
    }

    @Override
    public String getScriptToExecute() {
        return MessageFormat.format(content, values);
    }
}
