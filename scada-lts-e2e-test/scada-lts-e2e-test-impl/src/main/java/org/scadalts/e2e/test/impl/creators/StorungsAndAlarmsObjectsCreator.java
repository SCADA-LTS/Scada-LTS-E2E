package org.scadalts.e2e.test.impl.creators;


import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.service.impl.services.pointvalue.PointValueParams;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil.INVOKE_SET_DATA_POINT_VALUE_FROM_API_DID_NOT_SUCCEED;

@Log4j2
public class StorungsAndAlarmsObjectsCreator implements CreatorObject<DataSourcesPage, DataSourcesPage> {

    private final DataSourcePointObjectsCreator dataSourcePointObjectsCreator;
    private final DataSourcePointCriteria[] dataSourcePointCriterias;
    private final NavigationPage navigationPage;

    private DataSourcesPage dataSourcesPage;

    public StorungsAndAlarmsObjectsCreator(NavigationPage navigationPage, DataSourceCriteria dataSource,
                                           DataPointCriteria... dataPoints) {
        this.navigationPage = navigationPage;
        this.dataSourcePointCriterias = Stream.of(dataPoints)
                .map(a -> DataSourcePointCriteria.criteria(dataSource, a))
                .toArray(a -> new DataSourcePointCriteria[dataPoints.length]);
        this.dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriterias);
    }

    public StorungsAndAlarmsObjectsCreator(NavigationPage navigationPage, DataPointCriteria... dataPoints) {
        this.navigationPage = navigationPage;

        DataSourceCriteria dataSource = DataSourceCriteria.virtualDataSourceSecond();
        this.dataSourcePointCriterias = Stream.of(dataPoints)
                .map(a -> DataSourcePointCriteria.criteria(dataSource, a))
                .toArray(a -> new DataSourcePointCriteria[dataPoints.length]);
        this.dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSource, dataPoints);
    }

    @Override
    public DataSourcesPage deleteObjects() {
        try {
            _deleteAlarms();
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
        }
        dataSourcePointObjectsCreator.deleteObjects();
        return openPage();
    }

    public DataSourcesPage deleteAlaramsAndDataPoints() {
        try {
            _deleteAlarms();
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
        }
        dataSourcePointObjectsCreator.deleteDataPoints();
        return openPage();
    }

    @Override
    public DataSourcesPage createObjects() {
        dataSourcePointObjectsCreator.createObjects();
        return openPage();
    }

    @Override
    public DataSourcesPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage;
        }
        return dataSourcesPage.reopen();
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValues(VariationUnit<T> variation) {
        return setDataPointValues(variation.getVariation());
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValues(List<T> variation) {

        int iterations = 0;
        for (T unit : variation) {
            setDataPointValue(unit);
            logger.info("iteration: {}, for value: {}", ++iterations, unit);
        }
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValues(DataPointIdentifier identifier,
                                                                  List<T> variation) {

        int iterations = 0;
        for (T unit : variation) {
            setDataPointValue(identifier, unit);
            logger.info("iteration: {}, for value: {}", ++iterations, unit);
        }
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValue(T value) {
        String valueStr = String.valueOf(value);
        for(DataSourcePointCriteria criteria: dataSourcePointCriterias) {
            setDataPointValue(criteria.getDataPoint(), valueStr);
        }
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValue(DataPointCriteria dataPointCriteria, T value) {
        _setValue(dataPointCriteria, String.valueOf(value));
        _checkSetValue(dataPointCriteria, String.valueOf(value));
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValue(DataPointIdentifier identifier, T value) {
        DataPointCriteria dataPointCriteria = _get(identifier, dataSourcePointCriterias).orElseThrow(IllegalArgumentException::new);
        return setDataPointValue(dataPointCriteria, value);
    }

    private static Optional<DataPointCriteria> _get(DataPointIdentifier identifier,
                                                    DataSourcePointCriteria... dataSourcePointCriterias) {
        for(DataSourcePointCriteria dataSourcePointCriteria : dataSourcePointCriterias) {
            DataPointCriteria dataPointCriteria = dataSourcePointCriteria.getDataPoint();
            if(dataPointCriteria.getIdentifier().equals(identifier)) {
                return Optional.ofNullable(dataPointCriteria);
            }
        }
        return Optional.empty();
    }

    private static void _checkSetValue(DataPointCriteria criteria, String value) {
        PointValueParams pointValueParams = new PointValueParams(criteria.getXid().getValue());
        TestWithoutPageUtil.getDataPointValue(pointValueParams, value, TestImplConfiguration.waitingAfterSetPointValueMs);

    }

    private static void _setValue(DataPointCriteria criteria, String value) {
        E2eResponse<CmpParams> response = TestWithoutPageUtil.setDataPointValue(CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(value)
                .dataPointXid(criteria.getXid().getValue())
                .build());
        assertEquals(INVOKE_SET_DATA_POINT_VALUE_FROM_API_DID_NOT_SUCCEED, 200, response.getStatus());
    }

    private void _deleteAlarms() throws Throwable {
        PaginationParams paginationParams = PaginationParams.all();
        for (DataSourcePointCriteria criteria : dataSourcePointCriterias) {
            _setValue(criteria.getDataPoint(), "0");
            List<StorungAlarmResponse> alarms = StorungsAndAlarmsUtil
                    .getAlarmsAndStorungsSortByActivationTime(criteria.getDataPoint().getIdentifier(), paginationParams);
            for (StorungAlarmResponse alarm : alarms) {
                logger.info("delete: {}", alarm);
                AcknowledgeResponse response = StorungsAndAlarmsUtil.acknowledge(alarm.getId());
                logger.debug("delete... ok {}", response);
            }
        }
    }
}
