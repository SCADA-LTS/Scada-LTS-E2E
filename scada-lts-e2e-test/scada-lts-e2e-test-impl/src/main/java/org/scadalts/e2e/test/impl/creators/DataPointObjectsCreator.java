package org.scadalts.e2e.test.impl.creators;


import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.RangeValue;
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
import java.util.List;

@Log4j2
public class DataPointObjectsCreator implements CreatorObject<EditDataSourceWithPointListPage, EditDataSourceWithPointListPage> {

    private @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSourceCriteria;
    private final @NonNull DataPointCriteria[] dataPointCriterias;
    private DataSourcesPage dataSourcesPage;

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = new DataPointCriteria[]{DataPointCriteria.binaryNoChange()};
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourceCriteria dataSourceCriteria, @NonNull DataPointCriteria... dataPointCriterias) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourceCriteria;
        this.dataPointCriterias = dataPointCriterias;
    }

    public DataPointObjectsCreator(@NonNull NavigationPage navigationPage, @NonNull DataSourcePointCriteria dataSourcePointCriteria) {
        this.navigationPage = navigationPage;
        this.dataSourceCriteria = dataSourcePointCriteria.getDataSource();
        this.dataPointCriterias = new DataPointCriteria[]{dataSourcePointCriteria.getDataPoint()};
    }

    @Override
    public EditDataSourceWithPointListPage deleteObjects() {
        EditDataSourceWithPointListPage editDataSourceWithPointListPage = openPage();
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
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
        for (DataPointCriteria dataPointCriteria : dataPointCriterias) {
            if(!editDataSourceWithPointListPage.containsObject(dataPointCriteria.getIdentifier())) {
                EditDataPointPage editDataPointPage = _createDataPoint(editDataSourceWithPointListPage, dataPointCriteria);
                setProperties(dataPointCriteria, editDataPointPage);
                if(dataPointCriteria.isEnabled())
                    editDataPointPage.enableDataPoint(dataPointCriteria.getIdentifier());
            }
        }
        return editDataSourceWithPointListPage;
    }

    public List<DataSourcePointCriteria> data() {
        List<DataSourcePointCriteria> result = new ArrayList<>();
        for(DataPointCriteria dataPointCriteria: dataPointCriterias) {
            result.add(DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        }
        return result;
    }

    public void setProperties(DataPointCriteria dataPointCriteria, EditDataPointPage editDataPointPage) {
        DataPointProperties dataPointProperties = dataPointCriteria.getDataPointProperties();
        if(!dataPointProperties.isEmpty()) {
            DataPointPropertiesPage dataPointPropertiesPage = editDataPointPage.openDataPointProperties(dataPointCriteria.getIdentifier());
            setProperties(dataPointProperties, dataPointCriteria.getIdentifier().getType(),
                    dataPointPropertiesPage);
        }
    }

    public EditDataSourceWithPointListPage setProperties(DataPointProperties dataPointProperties, DataPointType dataPointType, DataPointPropertiesPage dataPointPropertiesPage) {

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
        if(!TestWithPageUtil.isLogged())
            navigationPage = TestWithPageUtil.openNavigationPage();
    }

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

    @Override
    public EditDataSourceWithPointListPage openPage() {
        if(dataSourcesPage == null) {
            dataSourcesPage = navigationPage.openDataSources();
            return dataSourcesPage.openDataSourceEditor(dataSourceCriteria.getIdentifier());
        }
        return dataSourcesPage.reopen()
                .openDataSourceEditor(dataSourceCriteria.getIdentifier());
    }

    private EditDataPointPage _createDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria criteria) {
        logger.info("create object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(), criteria.getIdentifier().getType(),
                criteria.getXid().getValue(), criteria.getClass().getSimpleName());
        return page.addDataPoint()
                .setName(criteria.getIdentifier())
                .setXid(criteria.getXid())
                .setSettable(criteria.isSettable())
                .setDataPointType(criteria.getIdentifier().getType())
                .setChangeType(criteria.getChangeType())
                .setStartValue(criteria)
                .save();
    }

    private EditDataSourceWithPointListPage _deleteDataPoint(EditDataSourceWithPointListPage page, DataPointCriteria criteria) {
        logger.info("delete object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(), criteria.getIdentifier().getType(),
                criteria.getXid().getValue(), criteria.getClass().getSimpleName());
        return page.openDataPointEditor(criteria.getIdentifier())
                .deleteDataPoint();
    }
}
