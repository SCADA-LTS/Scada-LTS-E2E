package org.scadalts.e2e.test.impl.utils;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.ServiceObjectFactory;
import org.scadalts.e2e.service.impl.services.StorungsAndAlarmsServiceObject;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.creators.StorungsAndAlarmsObjectsCreator;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Log4j2
public class StorungsAndAlarmsUtil {

    public final static String AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED = "Failure because: Expected after initializing the point with the value {0} then {1} {2} was generated.";
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
    public final static String EXPECTED_LARGER_OR_EQUALS_TO_X_ALARMS_STORUNGS_BUT_WAS_Y = "Failure because: Expected to be larger than or equal to {0} alarms/storungs but was {1}.";
    public final static String EXPECTED_ALARMS_STORUNGS_SORTED_DESCENDING_BY_ACTIVATION_TIME = "Failure because: Expected the alarms/stroungs to be sorted by descending activation-time";
    public final static String EXPECTED_ALARMS_STORUNGS_SORTED_DESCENDING_BY_INACTIVATION_TIME = "Failure because: Expected the alarms/stroungs to be sorted by descending inactivation-time";

    public final static String EXPECTED_ALARMS_STORUNGS_SORTED_ASCENDING_BY_ACTIVATION_TIME = "Failure because: Expected the alarms/stroungs to be sorted by ascending activation-time";
    public final static String EXPECTED_ACTIVE_ABOVE_BELOW_THEM_INACTIVE_LIVES_AND_SORTED_DESC = "Failure because: Expected active to be above, below them inactive lives and sorted.";
    public final static String EXPECTED_ONE_LIVE_IF_START_POINT_VALUE_IS_ONE_OTHER_ZERO = "Failure because: Expected 1 live if start point value is 1 other 0";

    public static List<StorungAlarmResponse> getAlarmsAndStorungsSortByActivationTime(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<StorungAlarmResponse> getResult = getResponse.getValue();
        return sortByActivationTimeDesc(StorungsAndAlarmsUtil._getAlarmsAndStorungsFor(identifier, getResult));
    }

    public static List<StorungAlarmResponse> getAlarmsAndStorungs(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<StorungAlarmResponse> getResult = getResponse.getValue();
        return StorungsAndAlarmsUtil._getAlarmsAndStorungsFor(identifier, getResult);
    }

    public static List<StorungAlarmResponse> getAlarmsAndStorungsSortByInactivationTime(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<StorungAlarmResponse> getResult = getResponse.getValue();
        return sortByInactivationTimeDesc(StorungsAndAlarmsUtil._getAlarmsAndStorungsFor(identifier, getResult));
    }

    public static List<StorungAlarmResponse> getAlarmsAndStorungsSortByIdDesc(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<StorungAlarmResponse> getResult = getResponse.getValue();
        return sortByIdDesc(StorungsAndAlarmsUtil._getAlarmsAndStorungsFor(identifier, getResult));
    }

    public static List<StorungAlarmResponse> getAlarmsAndStorungsSortByActivationTime(DataPointIdentifier identifier, Predicate<List<StorungAlarmResponse>> condition,
                                                                                      PaginationParams paginationParams) {
        return TestStabilityUtil.executeWhilePredicate(condition.negate(), StorungsAndAlarmsUtil::getAlarmsAndStorungsSortByActivationTime, identifier, paginationParams);
    }

    public static List<StorungAlarmResponse> sortByActivationTimeDesc(List<StorungAlarmResponse> list) {
        return list.stream()
                .sorted(_byActivationTimeIdDescComparator())
                .collect(Collectors.toList());
    }

    public static List<StorungAlarmResponse> sortByIdDesc(List<StorungAlarmResponse> list) {
        return list.stream()
                .sorted(_byIdDescComparator())
                .collect(Collectors.toList());
    }

    public static List<StorungAlarmResponse> sortByInactivationTimeDesc(List<StorungAlarmResponse> list) {
        return list.stream()
                .sorted(_byInactivationTimeIdDescComparator())
                .collect(Collectors.toList());
    }

    public static List<StorungAlarmResponse> sortByActivationTimeAsc(List<StorungAlarmResponse> list) {
        return list.stream()
                .sorted(Comparator.comparing(StorungAlarmResponse::getActivationTime))
                .collect(Collectors.toList());
    }


    public static List<StorungAlarmResponse> getReferenceStructure(List<StorungAlarmResponse> list) {
        List<StorungAlarmResponse> active = _filterActiveAlarmsAndStorungs(list);
        List<StorungAlarmResponse> inactive = _filterInactiveAlarmsAndStorungs(list);
        List<StorungAlarmResponse> ref = new ArrayList<>();
        ref.addAll(sortByActivationTimeDesc(active));
        ref.addAll(sortByInactivationTimeDesc(inactive));
        return ref;
    }

    public static List<StorungAlarmResponse> getStorungsAndAlarms(PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        return getResponse.getValue();
    }

    public static List<StorungAlarmResponse> getStorungsAndAlarms(Predicate<List<StorungAlarmResponse>> condition,
                                                                                      PaginationParams paginationParams) {
        return TestStabilityUtil.executeWhilePredicate(condition.negate(), StorungsAndAlarmsUtil::getStorungsAndAlarms, paginationParams);
    }

    public static List<StorungAlarmResponse> getStorungsAndAlarms(DataPointIdentifier identifier, Predicate<List<StorungAlarmResponse>> condition,
                                                                  PaginationParams paginationParams) {
        return TestStabilityUtil.executeWhilePredicate(condition.negate(), StorungsAndAlarmsUtil::getStorungsAndAlarms, identifier, paginationParams);
    }

    public static List<StorungAlarmResponse> getStorungsAndAlarms(DataPointIdentifier identifier, PaginationParams paginationParams) {
        E2eResponse<List<StorungAlarmResponse>> getResponse = TestWithoutPageUtil.getLiveAlarms(paginationParams,
                TestImplConfiguration.waitingAfterSetPointValueMs);
        assertEquals(INVOKE_GET_LIVES_FROM_API_DID_NOT_SUCCEED, 200, getResponse.getStatus());
        List<StorungAlarmResponse> getResult = getResponse.getValue();
        return StorungsAndAlarmsUtil._getAlarmsAndStorungsFor(identifier, getResult);
    }

    public static List<StorungAlarmResponse> getStorungsAndAlarms(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> result = getStorungsAndAlarms(paginationParams);
        return _check(result, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getActiveAlarms(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterActiveAlarms(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getInactiveAlarms(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterInactiveAlarms(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getInactiveAlarmsAndStorungs(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterInactiveAlarmsAndStorungs(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getActiveAlarmsAndStorungs(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterActiveAlarmsAndStorungs(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getInactiveAlarmsAndStorungs(PaginationParams paginationParams) {
        return _filterInactiveAlarmsAndStorungs(getStorungsAndAlarms(paginationParams));
    }

    public static List<StorungAlarmResponse> getActiveAlarmsAndStorungs(PaginationParams paginationParams) {
        return _filterActiveAlarmsAndStorungs(getStorungsAndAlarms(paginationParams));
    }

    public static List<StorungAlarmResponse> getActiveStorungs(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterActiveStorungs(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static List<StorungAlarmResponse> getInactiveStorungs(PaginationParams paginationParams, int numberLargerOrEqualsExpected) {
        List<StorungAlarmResponse> activateAlarms = _filterInactiveStorungs(getStorungsAndAlarms(paginationParams, numberLargerOrEqualsExpected));
        return _check(activateAlarms, numberLargerOrEqualsExpected);
    }

    public static AcknowledgeResponse acknowledge(String id) {
        try (StorungsAndAlarmsServiceObject storungsAndAlarmsServiceObject =
                     ServiceObjectFactory.newStorungsAndAlarmsServiceObject()) {
            Optional<E2eResponse<AcknowledgeResponse>> responseOpt = storungsAndAlarmsServiceObject.acknowledge(id,
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

    public static List<TestDataBatch> generateDataTest(int nWord, DataPointNotifierType prototype,
                                                       LoggingType loggingType, int startValue) {
        return VariationsGenerator.generate(1, nWord, startValue).stream()
                .map(a -> new TestDataBatch(a, prototype, loggingType))
                .collect(Collectors.toList());

    }

    public static List<TestDataBatch> generateDataTestEndValue(int nWord, DataPointNotifierType prototype,
                                                       LoggingType loggingType, int endValue) {
        return VariationsGenerator.generateEndValue(1, nWord, endValue).stream()
                .map(a -> new TestDataBatch(a, prototype, loggingType))
                .collect(Collectors.toList());

    }

    public static List<TestDataBatch> generateDataTestRandom(int nWord, DataPointNotifierType prototype,
                                                             LoggingType loggingType, int size) {
        return VariationsGenerator.generateRandom(1, nWord, size).stream()
                .map(a -> new TestDataBatch(a, prototype, loggingType))
                .collect(Collectors.toList());

    }

    public static TestDataBatch generateDataTestFromFile(Path path, DataPointNotifierType prototype) {
        return new TestDataBatch(VariationsGenerator.generateFromFile(path), prototype, LoggingType.ALL);
    }

    public static List<TestDataBatch> generateDataTestZeroToOnes(int nWord, DataPointNotifierType prototype,
                                                                 LoggingType loggingType, int size) {
        return VariationsGenerator.generateZeroToOnes(nWord, size).stream()
                .map(a -> new TestDataBatch(a, prototype, loggingType))
                .collect(Collectors.toList());

    }

    public static int getActiveAlarmsFromResponseNumber(List<StorungAlarmResponse> storungAlarmRespons) {
        int result = 0;
        for (StorungAlarmResponse res: storungAlarmRespons) {
            if(isActive(res))
                result++;
        }
        return result;
    }

    public static boolean isActive(StorungAlarmResponse alarm) {
        return alarm.getInactivationTime() == null
                || "1970-01-01 01:00:00".equalsIgnoreCase(alarm.getInactivationTime())
                || "".equalsIgnoreCase(alarm.getInactivationTime())
                || " ".equalsIgnoreCase(alarm.getInactivationTime());
    }

    public static int getInactiveAlarmsFromResponseNumber(List<StorungAlarmResponse> storungAlarmRespons) {
        int result = 0;
        for (StorungAlarmResponse res: storungAlarmRespons) {
            if(!isActive(res)) {
                Pattern pattern = Pattern.compile(RegexUtil.DATE_PSEUDO_ISO_REGEX);
                Matcher matcher = pattern.matcher(res.getInactivationTime());
                if (matcher.find())
                    result++;
            }
        }
        return result;
    }

    public static StorungsAndAlarmsObjectsCreator createDataSourcePointAndGetCreator(TestDataBatch testDataBatch, NavigationPage navigationPage) {

        DataSourceCriteria dataSource = DataSourceCriteria.virtualDataSourceSecond();
        DataPointCriteria dataPoint = DataPointCriteria.noChange(testDataBatch.getDataPointIdentifier(),
                String.valueOf(testDataBatch.getStartValue()),
                DataPointLoggingProperties.logging(testDataBatch.getLoggingType()));
        PaginationParams paginationParams = PaginationParams.all();

        StorungsAndAlarmsObjectsCreator storungsAndAlarmsObjectsCreator = new StorungsAndAlarmsObjectsCreator(navigationPage, dataSource, dataPoint);
        storungsAndAlarmsObjectsCreator.createObjects();

        List<StorungAlarmResponse> storungAlarmRespons = getAlarmsAndStorungsSortByActivationTime(testDataBatch.getDataPointIdentifier(),
                a -> a.size() == testDataBatch.getStartAlarmsNumber(), paginationParams);
        String msg = MessageFormat.format(AFTER_INITIALIZING_POINT_VALUE_WITH_X_THEN_Y_Z_WAS_GENERATED,
                testDataBatch.getStartValue(), testDataBatch.getStartAlarmsNumber(),
                testDataBatch.getDataPointNotifierType().getName());

        assertEquals(msg, testDataBatch.getStartAlarmsNumber(), storungAlarmRespons.size());

        return storungsAndAlarmsObjectsCreator;
    }

    public static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            logger.warn(e.getMessage(), e);
        }

    }

    private static Comparator<StorungAlarmResponse> _byInactivationTimeIdDescComparator() {
        return (a, b) -> {
            int result = b.getInactivationTime().compareTo(a.getInactivationTime());
            return result == 0 ? _byIdDescComparator().compare(a, b)  : result;
        };
    }

    private static Comparator<StorungAlarmResponse> _byActivationTimeIdDescComparator() {
        return (a, b) -> {
            int result = b.getActivationTime().compareTo(a.getActivationTime());
            return result == 0 ? _byIdDescComparator().compare(a, b) : result;
        };
    }

    private static Comparator<StorungAlarmResponse> _byIdDescComparator() {
        return (a, b) -> Integer.parseInt(b.getId()) - Integer.parseInt(a.getId());
    }

    private static List<StorungAlarmResponse> _check(List<StorungAlarmResponse> alarms, int numberLargerOrEqualsExpected) {
        String msg = MessageFormat.format(EXPECTED_LARGER_OR_EQUALS_TO_X_ALARMS_STORUNGS_BUT_WAS_Y, numberLargerOrEqualsExpected, alarms.size());
        assertNotNull(alarms);
        assertTrue(msg,alarms.size() >= numberLargerOrEqualsExpected);
        return alarms;
    }
/*
    private static boolean isActive(StorungAlarmResponse alarm) {
        return alarm.getInactivationTime() == null
                || "".equalsIgnoreCase(alarm.getInactivationTime())
                || " ".equalsIgnoreCase(alarm.getInactivationTime());
    }*/

    private static boolean _isAlarm(StorungAlarmResponse alarm) {
        String name = alarm.getName();
        return name.contains(" AL ");
    }

    private static boolean _isStorung(StorungAlarmResponse alarm) {
        String name = alarm.getName();
        return name.contains(" ST ");
    }


    private static List<StorungAlarmResponse> _filterInactiveStorungs(List<StorungAlarmResponse> list) {
        return list.stream().filter(a -> !isActive(a))
                .filter(StorungsAndAlarmsUtil::_isStorung)
                .collect(Collectors.toList());
    }

    private static List<StorungAlarmResponse> _filterInactiveAlarms(List<StorungAlarmResponse> list) {
        return list.stream().filter(a -> !isActive(a))
                .filter(StorungsAndAlarmsUtil::_isAlarm)
                .collect(Collectors.toList());
    }

    private static List<StorungAlarmResponse> _filterActiveStorungs(List<StorungAlarmResponse> list) {
        return list.stream().filter(StorungsAndAlarmsUtil::isActive)
                .filter(StorungsAndAlarmsUtil::_isStorung)
                .collect(Collectors.toList());
    }

    private static List<StorungAlarmResponse> _filterActiveAlarms(List<StorungAlarmResponse> list) {
        return list.stream().filter(StorungsAndAlarmsUtil::isActive)
                .filter(StorungsAndAlarmsUtil::_isAlarm)
                .collect(Collectors.toList());
    }

    private static List<StorungAlarmResponse> _filterActiveAlarmsAndStorungs(List<StorungAlarmResponse> list) {
        return list.stream().filter(StorungsAndAlarmsUtil::isActive)
                .collect(Collectors.toList());
    }

    private static List<StorungAlarmResponse> _filterInactiveAlarmsAndStorungs(List<StorungAlarmResponse> list) {
        return list.stream().filter(a -> !isActive(a))
                .collect(Collectors.toList());
    }


    private static List<StorungAlarmResponse> _getAlarmsAndStorungsFor(DataPointIdentifier identifier, List<StorungAlarmResponse> alarms) {
        List<StorungAlarmResponse> responses = new ArrayList<>();
        if(alarms == null) {
            return Collections.emptyList();
        }
        for (StorungAlarmResponse alarm: alarms) {
            if(alarm.getName().equalsIgnoreCase(identifier.getValue()))
                responses.add(alarm);
        }
        return responses;
    }
}
