package org.scadalts.e2e.page.core.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.scadalts.e2e.page.core.utils.E2eCookie;
import org.scadalts.e2e.page.core.utils.E2eWebDriverProvider;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface GetCookie {

    Logger LOGGER = LogManager.getLogger(GetCookie.class);

    default Optional<String> getSessionId() {
        return getCookies()
                .stream()
                .filter(a -> a.getName().equals("JSESSIONID"))
                .map(E2eCookie::getValue)
                .findFirst();
    }

    default List<E2eCookie> getCookies() {
        try {
            return E2eWebDriverProvider.manage()
                    .getCookies()
                    .stream()
                    .map(E2eCookie::new)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            return Collections.emptyList();
        }
    }
}
