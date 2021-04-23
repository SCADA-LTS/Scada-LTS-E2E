package org.scadalts.e2e.test.impl.config;

import org.junit.Before;
import org.junit.Test;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

public class AcknowledgeAllAlaramsAndStorungs {

    @Before
    public void config() {
        TestWithoutPageUtil.preparingTest();
    }

    @Test
    public void acknowledge_all_alarms_and_storungs() {

        PaginationParams paginationParams = PaginationParams.all();

        List<StorungAlarmResponse> storungAlarmResponses = getStorungsAndAlarms(paginationParams);

        for (StorungAlarmResponse storungAlarmResponse : storungAlarmResponses) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledge(storungAlarmResponse.getId());

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }
    }
}
