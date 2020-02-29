package org.scadalts.e2e.page.impl.criterias;

import java.util.Random;

public class XidUtil {

    public static String generateString(final int length, final String charSet) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            Random random = new Random();
            sb.append(charSet.charAt(random.nextInt(charSet.length())));
        }
        return sb.toString();
    }
}
