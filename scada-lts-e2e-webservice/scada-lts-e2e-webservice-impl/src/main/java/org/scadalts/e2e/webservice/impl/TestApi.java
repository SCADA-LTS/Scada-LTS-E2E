package org.scadalts.e2e.webservice.impl;


import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.CmpWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.LoginWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.cmp.CmpParams;
import org.scadalts.e2e.webservice.impl.services.login.LoginParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.net.MalformedURLException;
import java.net.URL;

@Log4j2
public class TestApi {

    public static void main(String[] args) throws MalformedURLException {

        E2eConfiguration.baseUrl = new URL("http://localhost:8080/ScadaBR/");

        LoginParams loginParams = LoginParams.builder()
                .userName(E2eConfiguration.userName)
                .password(E2eConfiguration.password)
                .build();

        try(LoginWebServiceObject loginWebServiceObject1 =
                    WebServiceObjectFactory.newLoginWebServiceObject(loginParams)) {

            E2eResponse<String> response = loginWebServiceObject1.login().orElse(new E2eResponse<>());
            logger.info(response);

            PointValueParams pointValueParams = new PointValueParams("DP_054378");

            try (PointValueWebServiceObject pointValueWebService =
                         WebServiceObjectFactory.newPointValueWebServiceObject(pointValueParams)) {
                E2eResponse<PointValueResponse> res = pointValueWebService.getValue().orElse(new E2eResponse<>());
                PointValueResponse response1 = res.getValue();
                logger.info(response1);
            }

            CmpParams cmpParams = CmpParams.builder()
                    .error("")
                    .resultOperationSave("")
                    .xid("DP_054378")
                    .value("12345")
                    .build();

            try (CmpWebServiceObject cmpWebServiceObject =
                         WebServiceObjectFactory.newCmpWebServiceObject(cmpParams)) {

                E2eResponse<CmpParams> res = cmpWebServiceObject.set().orElse(new E2eResponse<>());
                CmpParams cmpParams1 = res.getValue();
                //logger.info(res.readEntity(String.class));
                logger.info(res);
            }
        }
    }
}
