package org.scadalts.e2e.service.impl.services;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.scadalts.e2e.common.config.E2eConfiguration;

import javax.ws.rs.client.ClientBuilder;

public interface ServiceObjectFactory {

    static CmpServiceObject newCmpServiceObject() {
        return CmpServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    static LoginServiceObject newLoginServiceObject() {
        return LoginServiceObject.builder()
                .client(ClientBuilder.newClient())
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    static PointValueServiceObject newPointValueServiceObject() {
        return PointValueServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    static DataPointServiceObject newDataPointServiceObject() {
        return DataPointServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    static StorungsAndAlarmsServiceObject newStorungsAndAlarmsServiceObject() {
        return StorungsAndAlarmsServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }
}
