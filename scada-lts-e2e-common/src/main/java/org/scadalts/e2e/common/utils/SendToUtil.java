package org.scadalts.e2e.common.utils;

import org.scadalts.e2e.common.config.SendTo;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class SendToUtil {

    public static Set<SendTo> parse(String adresses) {
        String[] adressesArray = adresses.split(";");
        Set<SendTo> result = new HashSet<>();
        for (String adress : adressesArray) {
            result.add(_parse(adress));
        }
        return result;
    }

    private static SendTo _parse(String adressAndLang) {
        String[] data = adressAndLang.split("&lang=");
        return data.length == 1 ? SendTo.sendTo(data[0]) : SendTo.sendTo(data[0], Locale.forLanguageTag(data[1]));
    }
}
