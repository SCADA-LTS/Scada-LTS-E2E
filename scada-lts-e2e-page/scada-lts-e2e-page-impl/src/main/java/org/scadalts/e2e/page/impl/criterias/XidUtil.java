package org.scadalts.e2e.page.impl.criterias;

import java.util.Random;

public class XidUtil {

    private static Random random = new Random();

    public static String generateString(final int length, final String charSet) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            sb.append(charSet.charAt(random.nextInt(charSet.length())));
        }
        return sb.toString();
    }
}
