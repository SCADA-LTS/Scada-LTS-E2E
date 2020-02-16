package org.scadalts.e2e.test.impl.tests.check.eventdetectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.runners.E2eTestRunner;
import org.scadalts.e2e.webservice.core.exceptions.WebServiceObjectException;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.CmpWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.cmp.CmpParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;
import static org.scadalts.e2e.page.core.util.TypeParser.parseIntValueFormatted;

@RunWith(E2eTestRunner.class)
public class EventDetectorCheckTest {

    @Test
    public void test_check_detector() throws InterruptedException, WebServiceObjectException {

        //given:
        long valueExpected = new Random().nextInt(999);
        String value = String.valueOf(valueExpected);
        PointValueParams pointValueParams = new PointValueParams(TestImplConfiguration.dataPointToReadXid);
        CmpParams cmpParams = CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(value)
                .xid(TestImplConfiguration.dataPointToChangeXid)
                .build();

        //when:
        try (CmpWebServiceObject cmpWebServiceObject = WebServiceObjectFactory.newCmpWebServiceObject()) {

            Optional<E2eResponse<CmpParams>> responseOpt = cmpWebServiceObject.set(cmpParams);
            assertTrue(responseOpt.isPresent());

            E2eResponse<CmpParams> response = responseOpt.get();
            int status = response.getStatus();
            assertEquals(200, status);

            CmpParams result = response.getValue();
            assertNotNull(result);
            assertEquals("", result.getError());
            assertEquals(TestImplConfiguration.dataPointToChangeXid, result.getXid());

            int resultRaw = parseIntValueFormatted(result.getValue());
            assertEquals(valueExpected, resultRaw);
        }

        Thread.sleep(TestImplConfiguration.waitingAfterSetPointValueMs);

        //then:
        try (PointValueWebServiceObject pointValueWebServiceObject =
                     WebServiceObjectFactory.newPointValueWebServiceObject()) {

            Optional<E2eResponse<PointValueResponse>> responseOpt =
                    pointValueWebServiceObject.getValue(pointValueParams);
            assertTrue(responseOpt.isPresent());

            E2eResponse<PointValueResponse> response = responseOpt.get();
            int status = response.getStatus();
            PointValueResponse result = response.getValue();

            assertEquals(200, status);
            assertNotNull(result);
            assertEquals(TestImplConfiguration.dataPointToReadXid, result.getXid());

            int resultRaw = parseIntValueFormatted(result.getValue());
            assertEquals(valueExpected, resultRaw);
        }
    }
}
