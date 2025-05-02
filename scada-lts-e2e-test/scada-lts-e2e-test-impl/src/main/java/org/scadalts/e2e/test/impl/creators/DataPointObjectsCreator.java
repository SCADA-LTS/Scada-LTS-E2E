package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointChartRenderProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointLoggingProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointProperties;
import org.scadalts.e2e.page.impl.criterias.properties.DataPointTextRendererProperties;
import org.scadalts.e2e.page.impl.dicts.ChartRendererType;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.page.impl.dicts.TextRendererType;
import org.scadalts.e2e.page.impl.pages.datasource.DataSourcesPage;
import org.scadalts.e2e.page.impl.pages.datasource.EditDataSourceWithPointListPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.DataPointPropertiesPage;
import org.scadalts.e2e.page.impl.pages.datasource.datapoint.EditDataPointPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;
import org.scadalts.e2e.test.impl.utils.TestWithPageUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
public abstract class DataPointObjectsCreator<S extends DataSourceCriteria, P extends DataPointCriteria>
        implements CreatorObject<EditDataSourceWithPointListPage, EditDataSourceWithPointListPage> {

    private @NonNull NavigationPage navigationPage;
    private final @NonNull S dataSource;
    private final @NonNull List<P> dataPoints;
    private DataSourcesPage dataSourcesPage;

    @SafeVarargs
    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage,
                                   @NonNull S dataSource,
                                   P... dataPoints) {
        this.navigationPage = navigationPage;
        this.dataSource = dataSource;
        this.dataPoints = dataPoints == null ? Collections.emptyList() : Stream.of(dataPoints).collect(Collectors.toList());
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage,
                                   @NonNull S dataSource,
                                   List<P> dataPoints) {
        this.navigationPage = navigationPage;
        this.dataSource = dataSource;
        this.dataPoints = dataPoints;
    }

    @SafeVarargs
    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage,
                                   @NonNull DataSourcePointCriteria<S, P> dataSourcePoint,
                                   DataSourcePointCriteria<S, P>... dataSourcePoints) {
        this.navigationPage = navigationPage;
        this.dataSource = dataSourcePoint.getDataSource();
        this.dataPoints = Stream.concat(Stream.of(dataSourcePoint), Stream.of(dataSourcePoints))
                .map(DataSourcePointCriteria::getDataPoint)
                .collect(Collectors.toList());
    }

    @Override
    public EditDataSourceWithPointListPage deleteObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        for (P dataPointCriteria : dataPoints) {
            if(editDataSourceWithPointListPage.containsObject(dataPointCriteria.getIdentifier())) {
                _deleteDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
            }
        }
        return editDataSourceWithPointListPage;
    }

    @Override
    public EditDataSourceWithPointListPage createObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        return createObjects(editDataSourceWithPointListPage);
    }

    public EditDataSourceWithPointListPage createObjects(EditDataSourceWithPointListPage editDataSourceWithPointListPage) {
        for (P dataPointCriteria : dataPoints) {
            if(!editDataSourceWithPointListPage.containsObject(dataPointCriteria.getIdentifier())) {
                EditDataPointPage editDataPointPage = createDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
                setProperties(dataPointCriteria, editDataPointPage);
                if(dataPointCriteria.isEnabled())
                    editDataPointPage.enableDataPoint(dataPointCriteria.getIdentifier());
            }
        }
        return editDataSourceWithPointListPage;
    }

    public List<DataSourcePointCriteria<S, P>> data() {
        List<DataSourcePointCriteria<S, P>> result = new ArrayList<>();
        for(P dataPoint: dataPoints) {
            result.add(DataSourcePointCriteria.criteria(dataSource, dataPoint));
        }
        return result;
    }

    public void setProperties(P dataPointCriteria, EditDataPointPage editDataPointPage) {
        DataPointProperties dataPointProperties = dataPointCriteria.getDataPointProperties();
        if(!dataPointProperties.isEmpty()) {
            DataPointPropertiesPage dataPointPropertiesPage = editDataPointPage.openDataPointProperties(dataPointCriteria.getIdentifier());
            setProperties(dataPointProperties, dataPointCriteria.getIdentifier().getType(),
                    dataPointPropertiesPage);
        }
    }

    public EditDataSourceWithPointListPage setProperties(DataPointProperties dataPointProperties,
                                                         DataPointType dataPointType,
                                                         DataPointPropertiesPage dataPointPropertiesPage) {

        DataPointTextRendererProperties textRenderer = dataPointProperties.getTextRendererProperties();
        if(!textRenderer.isEmpty()) {
            _setTextRenderer(dataPointPropertiesPage, textRenderer);
        }

        DataPointChartRenderProperties chartRender = dataPointProperties.getChartRenderProperties();
        if(!chartRender.isEmpty()) {
            _setChartRenderer(dataPointPropertiesPage, chartRender);
        }

        DataPointLoggingProperties loggingProperties = dataPointProperties.getLoggingProperties();
        if(!loggingProperties.isEmpty()) {
            _setLogging(dataPointPropertiesPage, loggingProperties, dataPointType);
        }

        if(dataPointType == DataPointType.NUMERIC) {
            dataPointPropertiesPage.selectEngineeringUnit(dataPointProperties.getEngineeringUnits());
        }
        return dataPointPropertiesPage.saveDataPoint()
                .editDataSource();

    }

    @Override
    public void reload() {
        if (!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }

    @Override
    public EditDataSourceWithPointListPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSource.getIdentifier());
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSource.getIdentifier());
    }

    protected abstract EditDataPointPage createDataPoint(EditDataSourceWithPointListPage page, P dataPoint);

    private void _setLogging(DataPointPropertiesPage dataPointPropertiesPage,
                             DataPointLoggingProperties loggingProperties,
                             DataPointType dataPointType) {
        LoggingType loggingType = loggingProperties.getLoggingType();
        dataPointPropertiesPage.selectLoggingType(loggingType);
        if(loggingType == LoggingType.INTERVAL) {
            dataPointPropertiesPage.selectIntervalLoggingType(loggingProperties.getIntervalLoggingType());
            dataPointPropertiesPage.selectIntervalLoggingPeriodType(loggingProperties.getIntervalLoggingPeriodType());
            dataPointPropertiesPage.setIntervalLoggingPeriod(loggingProperties.getIntervalLoggingPeriod());
        }
        if(loggingType == LoggingType.ON_CHANGE && dataPointType == DataPointType.NUMERIC) {
            dataPointPropertiesPage.setLoggingTolerance(loggingProperties.getTolerance());
        }
        dataPointPropertiesPage.setLoggingDiscardExtremeValues(loggingProperties.isDiscardExtremeValues());
        if(loggingProperties.isDiscardExtremeValues()) {
            dataPointPropertiesPage.setLoggingDiscardLowLimit(loggingProperties.getDiscardLowLimit());
            dataPointPropertiesPage.setLoggingDiscardHighLimit(loggingProperties.getDiscardHighLimit());
        }
        if(loggingType != LoggingType.NONE) {
            dataPointPropertiesPage.selectLoggingPurgeType(loggingProperties.getPurgeType());
            dataPointPropertiesPage.setLoggingPurgePeriod(loggingProperties.getPurgePeriod());
        }
        dataPointPropertiesPage.setLoggingDefaultCacheSize(loggingProperties.getDefaultCacheSize());
    }

    private void _setChartRenderer(DataPointPropertiesPage dataPointPropertiesPage, DataPointChartRenderProperties chartRender) {
        ChartRendererType chartRendererType = chartRender.getChartRendererType();
        dataPointPropertiesPage.selectChartRendererType(chartRendererType);
        dataPointPropertiesPage.delay();

        if(chartRendererType == ChartRendererType.STATS || chartRendererType == ChartRendererType.IMAGE) {
            dataPointPropertiesPage.selectChartRendererPeriodType(chartRender.getPeriodType());
            dataPointPropertiesPage.setChartRendererPeriod(chartRender.getPeriod());
        }
        if(chartRendererType == ChartRendererType.STATS) {
            dataPointPropertiesPage.setChartRendererIncludeSum(chartRender.isIncludeSum());
        }
        if(chartRendererType == ChartRendererType.TABLE) {
            dataPointPropertiesPage.setChartRendererLimit(chartRender.getLimit());
        }
    }

    private void _setTextRenderer(DataPointPropertiesPage dataPointPropertiesPage, DataPointTextRendererProperties textRenderer) {
        TextRendererType textRendererType = textRenderer.getTextRendererType();
        dataPointPropertiesPage.selectTextRendererType(textRenderer.getTextRendererType());

        if (textRendererType != TextRendererType.PLAIN)
            dataPointPropertiesPage.setTextRendererFormat(textRenderer.getFormat());

        if (textRendererType == TextRendererType.PLAIN
                || textRendererType == TextRendererType.ANALOG)
            dataPointPropertiesPage.setTextRendererSuffix(textRenderer.getSuffix());

        if (textRendererType == TextRendererType.RANGE) {
            for (RangeValue rangeValue : textRenderer.getRangeValues()) {
                dataPointPropertiesPage.setTextRendererFrom(rangeValue.getFrom());
                dataPointPropertiesPage.setTextRendererTo(rangeValue.getTo());
                dataPointPropertiesPage.setTextRendererText(rangeValue.getText());
                dataPointPropertiesPage.setColour(rangeValue.getColour());
                dataPointPropertiesPage.addTextRendererRange();
            }
        }

        if (textRendererType == TextRendererType.TIME)
            dataPointPropertiesPage.setTextRendererTimeConversionExponent(textRenderer.getConversionExponent());
    }

    private EditDataSourceWithPointListPage _deleteDataPoint(EditDataSourceWithPointListPage page, P criteria) {
        logger.info("delete object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(), criteria.getIdentifier().getType(),
                criteria.getXid().getValue(), criteria.getClass().getSimpleName());
        return page.openDataPointEditor(criteria.getIdentifier())
                .deleteDataPoint();
    }
}
