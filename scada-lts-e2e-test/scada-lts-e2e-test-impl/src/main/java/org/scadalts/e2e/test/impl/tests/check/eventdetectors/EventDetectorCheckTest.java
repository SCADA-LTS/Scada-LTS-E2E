package org.scadalts.e2e.test.impl.tests.check.eventdetectors;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.webservice.core.services.E2eResponse;
import org.scadalts.e2e.webservice.impl.services.CmpWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.PointValueWebServiceObject;
import org.scadalts.e2e.webservice.impl.services.WebServiceObjectFactory;
import org.scadalts.e2e.webservice.impl.services.cmp.CmpParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.webservice.impl.services.pointvalue.PointValueResponse;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

import static org.junit.Assert.*;

@Log4j2
public class EventDetectorCheckTest {

    @Test
    public void test_check_detector() {

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
        try (CmpWebServiceObject cmpWebServiceObject = WebServiceObjectFactory.newCmpWebServiceObject(cmpParams)) {

            Optional<E2eResponse<CmpParams>> responseOpt = cmpWebServiceObject.set();
            assertTrue(responseOpt.isPresent());

            E2eResponse<CmpParams> response = responseOpt.get();
            int status = response.getStatus();
            CmpParams result = response.getValue();

            assertEquals(200, status);
            assertNotNull(result);
            assertEquals("", result.getError());
            assertEquals(TestImplConfiguration.dataPointToChangeXid, result.getXid());

            int resultRaw = parseIntValueFormatted(result.getValue());
            assertEquals(valueExpected, resultRaw);
        }

        E2eAbstractRunnable.getNavigationPage()
                .waitOnPage(TestImplConfiguration.waitingAfterSetPointValueMs);

        //then:
        try (PointValueWebServiceObject pointValueWebServiceObject = WebServiceObjectFactory.newPointValueWebServiceObject(pointValueParams)) {

            Optional<E2eResponse<PointValueResponse>> responseOpt = pointValueWebServiceObject.getValue();
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

    private int parseIntValueFormatted(String value) {
        return new BigDecimal(value).intValue();
    }
}
