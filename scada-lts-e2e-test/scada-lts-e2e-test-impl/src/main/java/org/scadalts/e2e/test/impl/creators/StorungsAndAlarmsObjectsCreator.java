package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.storungs.AcknowledgeResponse;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;
import org.scadalts.e2e.service.impl.services.storungs.PaginationParams;
import org.scadalts.e2e.service.impl.services.cmp.CmpParams;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.StorungsAndAlarmsUtil;
import org.scadalts.e2e.test.impl.utils.TestWithoutPageUtil;

import java.util.List;
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
            deleteAlarms();
        } catch (Throwable throwable) {
            logger.warn(throwable.getMessage(), throwable);
        }
        dataSourcePointObjectsCreator.deleteObjects();
        return openPage();
    }

    public DataSourcesPage deleteAlaramsAndDataPoints() {
        try {
            deleteAlarms();
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
        for (T unit : variation) {
            setDataPointValue(unit);
        }
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValue(T value) {
        DataSourcesPage dataSourcesPage = openPage();
        for(DataSourcePointCriteria criteria: dataSourcePointCriterias) {
            setValue(criteria.getDataPoint(), String.valueOf(value));
            dataSourcesPage.waitOnPage(1000);
        }
        return this;
    }

    public <T> StorungsAndAlarmsObjectsCreator setDataPointValue(DataPointCriteria dataPointCriteria, T value) {
        setValue(dataPointCriteria, String.valueOf(value));
        return this;
    }

    private static void setValue(DataPointCriteria criteria, String value) {
        E2eResponse<CmpParams> response = TestWithoutPageUtil.setDataPointValue(CmpParams.builder()
                .error("")
                .resultOperationSave("")
                .value(value)
                .dataPointXid(criteria.getXid().getValue())
                .build());
        assertEquals(INVOKE_SET_DATA_POINT_VALUE_FROM_API_DID_NOT_SUCCEED, 200, response.getStatus());
    }

    private void deleteAlarms() throws Throwable {
        PaginationParams paginationParams = PaginationParams.builder().offset(0).limit(9999).build();
        for (DataSourcePointCriteria criteria : dataSourcePointCriterias) {
            setValue(criteria.getDataPoint(), "0");
            List<StorungAlarmResponse> alarms = StorungsAndAlarmsUtil
                    .getAlarmsSortByActivationTime(criteria.getDataPoint().getIdentifier(), paginationParams);
            for (StorungAlarmResponse alarm : alarms) {
                logger.info("delete: {}", alarm);
                AcknowledgeResponse response = StorungsAndAlarmsUtil.acknowledgeAlarm(alarm.getId());
                logger.debug("delete... ok {}", response);
            }
        }
    }
}
