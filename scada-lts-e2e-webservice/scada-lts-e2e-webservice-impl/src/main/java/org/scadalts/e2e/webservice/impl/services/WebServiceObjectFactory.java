package org.scadalts.e2e.webservice.impl.services;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.webservice.impl.services.cmp.CmpParams;
import org.scadalts.e2e.webservice.impl.services.login.LoginParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;

import javax.ws.rs.client.ClientBuilder;

public class WebServiceObjectFactory {

    public static CmpWebServiceObject newCmpWebServiceObject(CmpParams cmpParams) {
        return CmpWebServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .cmpParams(cmpParams)
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    public static LoginWebServiceObject newLoginWebServiceObject(LoginParams loginParams) {
        return LoginWebServiceObject.builder()
                .client(ClientBuilder.newClient())
                .loginParams(loginParams)
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }

    public static PointValueWebServiceObject newPointValueWebServiceObject(PointValueParams pointValueParams) {
        return PointValueWebServiceObject.builder()
                .client(ClientBuilder.newClient()
                        .register(new JacksonJsonProvider()))
                .pointValueParams(pointValueParams)
                .baseUrl(E2eConfiguration.baseUrl)
                .build();
    }
}
