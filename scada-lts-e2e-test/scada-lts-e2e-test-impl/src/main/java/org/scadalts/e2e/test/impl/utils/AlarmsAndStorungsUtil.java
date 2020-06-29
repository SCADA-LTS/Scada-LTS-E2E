package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.common.utils.VariationsGenerator;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AlarmsAndStorungsUtil {

    public final static String AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED = "Failure because: After initializing the point with the value {0} then {1} {2} was generated.";
    public final static String INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to invoke acknowledge from API did not succeed.";
    public final static String INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to invoke get lives from API did not succeed.";
    public final static String INVOKE_ACKNOWLEDGE_ON_ACTIVE_ALARM_DELETE_IT_FROM_LIVE = "Failure because: Invoke acknowledge on active alarm/storung delete it from live.";
    public final static String AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z = "Failure because: After changing point values by sequence: {0}, then the number of {1} live different from {2}.";
    public final static String EXPECTED_INACTIVE_ALARM = "Failure because: Expected inactive alarm/stroung.";
    public final static String EXPECTED_ACTIVE_ALARM = "Failure because: Expected active alarm/stroung.";
    public final static String ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE = "Failure because: Alarm/stroung inactive not removed from live.";
    public final static String ALARM_ACTIVE_REMOVED_FROM_LIVE = "Failure because: Alarm/stroung active removed from live.";
    public final static String EXPECTED_DATE_ISO = "Failure because: Expected date ISO.";
    public final static String EXPECTED_X_ALARMS_STORUNGS = "Failure because: Expected {0} alarms/stroungs.";

    public final static String EXPECTED_ONE_LIVE_IF_START_POINT_VALUE_IS_ONE_OTHER_ZERO = "Failure because: Expected 1 live if start point value is 1 other 0";

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

    public static List<AlarmResponse> getAlarms(DataPointIdentifier identifier, Predicate<List<AlarmResponse>> expected,
                                                 PaginationParams paginationParams) {
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams, expected);
        List<AlarmResponse> getResult = getResponse.getValue();
        return AlarmsAndStorungsUtil.getAlarmsFor(identifier, getResult).stream()
                .sorted((a, b) -> b.getActivationTime().compareTo(a.getActivationTime()))
                .collect(Collectors.toList());
    }

    public static int calculateRisingSlopes(VariationUnit<Integer> variation, int startValue) {
        return calculateRisingSlopes(variation.getVariation(), startValue);
    }

    public static int calculateRisingSlopes(List<Integer> list, int startValue) {
        int result = 0;
        int pre = startValue;
        for (Integer current : list) {
            if (!current.equals(pre) && pre < current) {
                result++;
            }
            pre = current;
        }
        return result;
    }

    public static List<TestDataBatch> generateDataTest(int nWord, DataPointNotifierType prototype, int startValue) {
        return VariationsGenerator.generate(1, nWord, startValue).stream()
                .map(a -> new TestDataBatch(a, prototype))
                .collect(Collectors.toList());

    }

    public static void main(String[] args) {

        List<Integer> values = new ArrayList<>();
        values.addAll(Arrays.asList(new Integer[]{0,1,0,1}));
        System.out.println(calculateRisingSlopes(values, 0));
    }
}
