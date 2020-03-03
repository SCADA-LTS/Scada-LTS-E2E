package org.scadalts.e2e.test.core.matchers;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.regex.Pattern;

public class ContainsRegex extends TypeSafeMatcher<String> {

    private final Pattern pattern;

    public ContainsRegex(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    @Override
    protected boolean matchesSafely(String body) {
        return pattern.matcher(body).find();
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("contains a phrase-matched fragment: '" + pattern.pattern() + "'");
    }

    public static ContainsRegex containsRegex(String regex) {
        return new ContainsRegex(regex);
    }
}
