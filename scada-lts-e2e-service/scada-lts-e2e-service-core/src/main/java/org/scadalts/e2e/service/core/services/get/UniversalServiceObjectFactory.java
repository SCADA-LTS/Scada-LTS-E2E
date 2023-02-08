package org.scadalts.e2e.service.core.services.get;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.scadalts.e2e.common.core.config.E2eConfiguration;

import javax.ws.rs.client.ClientBuilder;

public class UniversalServiceObjectFactory {

    public static GetServiceObject newGetServiceObject() {
        return GetServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }
}
