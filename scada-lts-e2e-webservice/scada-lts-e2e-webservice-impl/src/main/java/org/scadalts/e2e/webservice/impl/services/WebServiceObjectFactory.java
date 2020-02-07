package org.scadalts.e2e.webservice.impl.services;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.scadalts.e2e.common.config.E2eConfiguration;

import javax.ws.rs.client.ClientBuilder;

public class WebServiceObjectFactory {

    public static CmpWebServiceObject newCmpWebServiceObject() {
        return CmpWebServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    public static LoginWebServiceObject newLoginWebServiceObject() {
        return LoginWebServiceObject.builder()
                .client(ClientBuilder.newClient())
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    public static PointValueWebServiceObject newPointValueWebServiceObject() {
        return PointValueWebServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }
}
