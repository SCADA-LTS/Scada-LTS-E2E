package org.scadalts.e2e.service.impl;


import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.service.core.exceptions.WebServiceObjectException;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.CmpServiceObject;
import org.scadalts.e2e.service.impl.services.LoginServiceObject;
import org.scadalts.e2e.service.impl.services.PointValueServiceObject;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueResponse;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

@Log4j2
public class TestApi {

    public static void main(String[] args) throws MalformedURLException, WebServiceObjectException {

        E2eConfiguration.baseUrl = new URL("http://localhost:8080/ScadaBR/");

        try(LoginServiceObject loginServiceObject1 =
                    ServiceObjectFactory.newLoginServiceObject()) {

            LoginParams loginParams = LoginParams.builder()
                    .userName(E2eConfiguration.userName)
                    .password(E2eConfiguration.password)
                    .build();

            E2eResponse<String> response = loginServiceObject1.login(loginParams, 10000).orElse(new E2eResponse<>());
            logger.info(response);

            double valueExpectedRaw = new Random().nextDouble();
            String valueExpected = String.valueOf(valueExpectedRaw);

            try (CmpServiceObject cmpServiceObject =
                         ServiceObjectFactory.newCmpServiceObject()) {

                CmpParams cmpParams = CmpParams.builder()
                        .error("")
                        .resultOperationSave("")
                        .dataPointXid("DP_054378")
                        .value(valueExpected)
                        .build();
                E2eResponse<CmpParams> res = cmpServiceObject.set(cmpParams, 10000).orElse(new E2eResponse<>());
                CmpParams cmpParams1 = res.getValue();
                //logger.info(res.readEntity(String.class));
                logger.info(res);
            }

            try (PointValueServiceObject pointValueWebService =
                         ServiceObjectFactory.newPointValueServiceObject()) {

                PointValueParams pointValueParams = new PointValueParams("DP_054378");
                E2eResponse<PointValueResponse> res = pointValueWebService
                        .getValue(pointValueParams, 300000, valueExpectedRaw).orElse(new E2eResponse<>());
                logger.info(res);
                PointValueResponse response1 = res.getValue();
                String result = response1.getFormattedValue();
                logger.info("expected: {}", valueExpected);
                logger.info("result: {}", result);
                logger.info(Objects.equals(valueExpected, result));
                logger.info(res);
            }

        }
    }
}
