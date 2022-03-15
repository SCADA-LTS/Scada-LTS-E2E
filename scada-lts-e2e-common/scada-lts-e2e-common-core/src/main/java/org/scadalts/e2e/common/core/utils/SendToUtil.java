package org.scadalts.e2e.common.core.utils;

import org.scadalts.e2e.common.core.config.SendTo;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SendToUtil {

    private SendToUtil() {}

    public static Set<SendTo> parse(String adresses) {
        String[] addresses = adresses.split(";");
        Set<SendTo> result = new HashSet<>();
        for (String address : addresses) {
            result.add(_parse(address));
        }
        return result;
    }

    private static SendTo _parse(String addressWith) {
        String lang = lang(addressWith);
        String dest = dest(addressWith);
        String address = address(addressWith);
        if(!lang.isEmpty() && !dest.isEmpty()) {
            return SendTo.sendTo(address, Locale.forLanguageTag(lang), SendTo.Destination.valueOf(dest.toUpperCase()));
        }
        if(!lang.isEmpty()) {
            return SendTo.emailSendTo(address, Locale.forLanguageTag(lang));
        }
        if(!dest.isEmpty()) {
            return SendTo.sendTo(address, SendTo.Destination.valueOf(dest.toUpperCase()));
        }
        return SendTo.emailSendTo(address);
    }

    private static String dest(String address) {
        return get(address, "&dest=");
    }

    private static String lang(String address) {
        return get(address, "&lang=");
    }

    private static String get(String address, String attribute) {
        if(address.contains(attribute)) {
            String first = firstGroup(address, "(?<=" + attribute + ")(.*)(?=&)");
            if(first.isEmpty()) {
                first = firstGroup(address, "(?<=" + attribute + ")(.*)(?=)");
                if (first.isEmpty())
                    return "";
                return first;
            }
            return first;
        }
        return "";
    }

    private static String firstGroup(String address, String patternText) {
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(address);
        if(matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    private static String address(String address) {
        return address.split("&")[0];
    }
}
