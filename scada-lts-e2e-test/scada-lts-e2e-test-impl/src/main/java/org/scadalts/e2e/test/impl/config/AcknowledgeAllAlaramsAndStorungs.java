package org.scadalts.e2e.test.impl.config;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.runners.TestWithPageRunner;
import org.scadalts.e2e.test.impl.runners.TestWithoutPageRunner;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.*;

@RunWith(TestWithPageRunner.class)
public class AcknowledgeAllAlaramsAndStorungs {

    @Test
    public void acknowledge_all_alarms_and_storungs() {

        PaginationParams paginationParams = PaginationParams.builder()
                .limit(9999)
                .offset(0)
                .build();

        List<StorungAlarmResponse> storungAlarmResponses = getStorungsAndAlarms(paginationParams);

        for (StorungAlarmResponse storungAlarmResponse : storungAlarmResponses) {

            AcknowledgeResponse result = StorungsAndAlarmsUtil.acknowledge(storungAlarmResponse.getId());

            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, "OK", result.getRequestStatus());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR, "none", result.getError());
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID, storungAlarmResponse.getId(), result.getId());
        }
    }
}
