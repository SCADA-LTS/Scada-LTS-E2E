package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AlarmsAndStorungsUtil {

    public final static String THERE_ARE_NO_ALARMS_BEFORE_CHANGING_THE_POINT_VALUE = "Failure because: There are no alarms/stroungs before changing the point value.";
    public final static String AN_ATTEMPT_TO_CALL_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to call acknowledge from API did not succeed.";
    public final static String CALLING_ACKNOWLEDGE_ON_AN_ACTIVE_ALARM_DELETE_IT_FROM_LIVE = "Failure because: Calling acknowledge on an active alarm/storung delete it from live.";
    public final static String AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_THE_NUMBER_OF_ALARMS_LIVE_DIFFERENT_FROM_Y = "Failure because: After changing point values by sequence: {0}, then the number of alarms/stroungs live different from {1}.";
    public final static String EXPECTED_INACTIVE_ALARM = "Failure because: Expected inactive alarm/stroung.";
    public final static String EXPECTED_ACTIVE_ALARM = "Failure because: Expected active alarm/stroung.";
    public final static String ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE = "Failure because: Alarm/stroung inactive not removed from live.";
    public final static String ALARM_ACTIVE_REMOVED_FROM_LIVE = "Failure because: Alarm/stroung active removed from live.";
    public final static String EXPECTED_DATE_ISO = "Failure because: Expected date ISO.";


    public static List<AlarmResponse> getAlarmsFor(DataPointIdentifier identifier, List<AlarmResponse> alarms) {
        List<AlarmResponse> responses = new ArrayList<>();
        if(alarms == null) {
            return Collections.emptyList();
        }
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

    public static List<PermutationTestData> generateDataTest(int nWord, DataPointNotifierType prototype) {
        return PermutationGenerator.generate(1, nWord).stream()
                .map(a -> new PermutationTestData(a, IdentifierObjectFactory.dataPointNotifierBinaryTypeName(prototype)))
                .collect(Collectors.toList());

    }
}
