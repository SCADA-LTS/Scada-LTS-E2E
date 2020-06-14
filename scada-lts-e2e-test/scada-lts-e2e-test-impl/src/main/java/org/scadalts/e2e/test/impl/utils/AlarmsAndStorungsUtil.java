package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AlarmsAndStorungsUtil {

    public static List<AlarmResponse> getAlarmsFor(DataPointIdentifier identifier, List<AlarmResponse> alarms) {
        List<AlarmResponse> responses = new ArrayList<>();
        for (AlarmResponse alarm: alarms) {
            if(alarm.getName().equalsIgnoreCase(identifier.getValue()))
                responses.add(alarm);
        }
        return responses;
    }

    public static List<AlarmResponse> getAlarms(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(200, getResponse.getStatus());
        List<AlarmResponse> getResult = getResponse.getValue();
        return AlarmsAndStorungsUtil.getAlarmsFor(identifier, getResult);
    }
}
