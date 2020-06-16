package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<AlarmResponse> getResult = getResponse.getValue();
        return AlarmsAndStorungsUtil.getAlarmsFor(identifier, getResult).stream()
                .sorted((a, b) -> b.getActivationTime().compareTo(a.getActivationTime()))
                .collect(Collectors.toList());
    }

    public static int calculateRisingSlopes(List<Integer> list) {
        int result = 0;
        Integer pre = 0;
        for (int i = 0; i < list.size(); i++) {
            Integer current = list.get(i);
            if(!current.equals(pre) && pre < current) {
                result++;
            }
            pre = current;
        }
        return result;
    }

    public static List<PermutationTestData> generateDataTest(int nWords, DataPointIdentifier prototype) {
        return PermutationGenerator.generate(1, nWords).stream()
                .map(a -> new PermutationTestData(a, prototype.newIdentifier(IdentifierObjectFactory.unique())))
                .collect(Collectors.toList());

    }
}
