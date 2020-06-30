package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.common.utils.VariationsGenerator;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.StorungsAndAlarmsServiceObject;
import org.scadalts.e2e.service.impl.services.alarms.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.alarms.AlarmResponse;
import org.scadalts.e2e.service.impl.services.alarms.PaginationParams;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class AlarmsAndStorungsUtil {

    public final static String AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED = "Failure because: After initializing the point with the value {0} then {1} {2} was generated.";
    public final static String INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to invoke acknowledge from API did not succeed.";
    public final static String INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_OK = "Failure because: An attempt to invoke acknowledge from API did not ok.";
    public final static String INVOKE_ACKNOWLEDGE_FROM_API_CAUSES_ERROR = "Failure because: An attempt to invoke acknowledge from API causes an error.";
    public final static String INVOKE_ACKNOWLEDGE_FROM_API_RETURNING_OTHER_ID = "Failure because: An attempt to invoke acknowledge from API returns the id of an object other.";
    public final static String INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to invoke get lives from API did not succeed.";
    public final static String INVOKE_ACKNOWLEDGE_ON_ACTIVE_ALARM_DELETE_IT_FROM_LIVE = "Failure because: Invoke acknowledge on active alarm/storung delete it from live.";
    public final static String AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_LIVE_DIFFERENT_FROM_Z = "Failure because: After changing point values by sequence: {0}, then the number of {1} live different from {2}.";
    public final static String AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_ACTIVE_DIFFERENT_FROM_Z = "Failure because: After changing point values by sequence: {0}, then the number of {1} active different from {2}.";
    public final static String AFTER_CHANGING_POINT_VALUES_BY_SEQUENCE_X_THEN_NUMBER_OF_Y_INACTIVE_DIFFERENT_FROM_Z = "Failure because: After changing point values by sequence: {0}, then the number of {1} inactive different from {2}.";
    public final static String INVOKE_SET_DATA_POINT_VALUE_FROM_API_DID_NOT_SUCCEED = "Failure because: An attempt to invoke set data point value from API did not succeed.";

    public final static String EXPECTED_THAT_INVOKE_ACKNOWLEDGE_NO_CHANGE_STATE_LIVE = "Failure because: Expected that invoke method acknowledge would not change the status of live alarms.";
    public final static String EXPECTED_INACTIVE_ALARM = "Failure because: Expected inactive alarm/storung.";

    public final static String EXPECTED_ACTIVE_ALARM = "Failure because: Expected active alarm/storung.";
    public final static String ALARM_INACTIVE_NOT_REMOVED_FROM_LIVE = "Failure because: Alarm/storung inactive not removed from live.";
    public final static String ALARM_ACTIVE_REMOVED_FROM_LIVE = "Failure because: Alarm/storung active removed from live.";
    public final static String EXPECTED_DATE_ISO = "Failure because: Expected date ISO.";
    public final static String EXPECTED_X_ALARMS_STORUNGS = "Failure because: Expected {0} alarms/storungs.";

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

    public static List<AlarmResponse> getAlarmsSortByActivationTime(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<AlarmResponse> getResult = getResponse.getValue();
        return AlarmsAndStorungsUtil.getAlarmsFor(identifier, getResult).stream()
                .sorted((a, b) -> b.getActivationTime().compareTo(a.getActivationTime()))
                .collect(Collectors.toList());
    }

    public static List<AlarmResponse> getAlarmsSortByActivationTime(DataPointIdentifier identifier, Predicate<List<AlarmResponse>> expected,
                                                                    PaginationParams paginationParams) {
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams, expected);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<AlarmResponse> getResult = getResponse.getValue();
        return AlarmsAndStorungsUtil.getAlarmsFor(identifier, getResult).stream()
                .sorted((a, b) -> b.getActivationTime().compareTo(a.getActivationTime()))
                .collect(Collectors.toList());
    }

    public static List<AlarmResponse> getAlarms(PaginationParams paginationParams) {
        E2eResponse<List<AlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        return getResponse.getValue();
    }

    public static AcknowledgeResponse acknowledgeAlarm(String id) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<AcknowledgeResponse>> responseOpt = storungsAndAlarmsServiceObject.acknowledgeAlarm(id,
                    TestImplConfiguration.waitingAfterSetPointValueMs);
            E2eResponse<AcknowledgeResponse> getResponse = responseOpt.orElseGet(E2eResponse::empty);
            assertEquals(INVOKE_ACKNOWLEDGE_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
            return getResponse.getValue();
        }
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

    public static int getNumberActiveAlarmsFromResponse(List<AlarmResponse> alarmResponses) {
        int result = 0;
        for (AlarmResponse res: alarmResponses) {
            if(res.getInactivationTime().equalsIgnoreCase("")
                    || res.getInactivationTime().equalsIgnoreCase(" "))
                result++;
        }
        return result;
    }

    public static int getNumberInactiveAlarmsFromResponse(List<AlarmResponse> alarmResponses) {
        int result = 0;
        for (AlarmResponse res: alarmResponses) {
            Pattern pattern = Pattern.compile(DateValidation.DATE_ISO_REGEX);
            Matcher matcher = pattern.matcher(res.getInactivationTime());
            if(matcher.find())
                result++;
        }
        return result;
    }
}
