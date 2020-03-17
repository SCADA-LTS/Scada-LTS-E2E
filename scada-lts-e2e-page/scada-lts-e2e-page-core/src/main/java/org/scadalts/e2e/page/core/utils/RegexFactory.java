package org.scadalts.e2e.page.core.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;

@Log4j2
public class RegexFactory {

    public static String between(IdentifierObject source, IdentifierObject target) {
        String regex = source.getValue() + "(.+?)" + target.getValue();
        logger.debug("regex: {}", regex);
        return regex;
    }

    public static String betweenIdentifierType(IdentifierObject identifier, DictionaryObject type) {
        String regex = identifier.getValue() + ((type == DictionaryObject.ANY) ? "" : "(.+?)" + type.getName());
        logger.debug("regex: {}", regex);
        return regex;
    }

    public static String identifier(IdentifierObject identifier) {
        String regex = identifier.getValue();
        logger.debug("regex: {}", regex);
        return regex;
    }

    public static String betweenNameAndType(String value, DictionaryObject type) {
        String regex = value + ((type == DictionaryObject.ANY) ? "" : "(.+?)" + type.getName());
        logger.debug("regex: {}", regex);
        return regex;
    }
}
