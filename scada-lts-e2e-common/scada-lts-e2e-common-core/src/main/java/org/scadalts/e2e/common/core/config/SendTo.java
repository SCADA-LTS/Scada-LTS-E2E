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

    public static SendTo sendTo(String adress) {
        return new SendTo(adress, Locale.getDefault());
    }

    public static SendTo sendTo(String adress, Locale locale) {
        return new SendTo(adress, locale);
    }

    @Override
    public String toString() {
        return MessageFormat.format("(adress={0}, locale={1})", adress, locale);
    }
}
