package org.scadalts.e2e.service.core.services.get;

import java.text.MessageFormat;

public class GetConfig {

    private final String endpoint;

    public GetConfig(String uri, String... params) {
        this.endpoint = MessageFormat.format(uri, params);
    }

    public String getEndpoint() {
        return endpoint;
    }
}
