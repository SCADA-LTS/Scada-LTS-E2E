package org.scadalts.e2e.common.core.config;

import lombok.*;

import java.text.MessageFormat;
import java.util.Locale;


@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SendTo {

    private final String adress;
    private final Locale locale;
    private final Destination dest;

    public static SendTo emailSendTo(String adress) {
        return new SendTo(adress, Locale.getDefault(), Destination.EMAIL);
    }

    public static SendTo emailSendTo(String adress, Locale locale) {
        return new SendTo(adress, locale, Destination.EMAIL);
    }

    public static SendTo sendTo(String adress, Locale locale, Destination desc) {
        return new SendTo(adress, locale, desc);
    }

    public static SendTo sendTo(String adress, Destination desc) {
        return new SendTo(adress, Locale.getDefault(), desc);
    }

    @Override
    public String toString() {
        return MessageFormat.format("(address={0}, locale={1}, dest={2})", adress, locale, dest);
    }

    public enum Destination {
        SMS, EMAIL
    }
}
