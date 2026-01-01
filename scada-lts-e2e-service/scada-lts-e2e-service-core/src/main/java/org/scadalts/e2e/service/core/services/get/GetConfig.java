package org.scadalts.e2e.service.core.services.get;

import java.net.URL;
import java.text.MessageFormat;

public class GetConfig {

    private final String endpoint;

    public GetConfig(String uri, String... params) {
        this.endpoint = MessageFormat.format(uri, params);
    }

    public String getEndpoint(URL baseUrl) {
        return baseUrl + endpoint;
    }
}
