package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.core.utils.ExecutorUtil;
import org.scadalts.e2e.common.core.utils.FileUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.InternalDataPointAttributeType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.InternalDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.JmxDataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Data
public class ConfigureMonitorCommand implements Command<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);

    }

    private void _execute() {
        UpdateDataSourceCriteria dataSourceCriteria = UpdateDataSourceCriteria.criteriaSecond(new DataSourceIdentifier("Scada perf monitor", DataSourceType.INTERNAL_DATA_SOURCE));

        Properties properties = new Properties();
        FileUtil.getFileFromJar("groovy/groovy-config.properties").ifPresent(groovyConfigFile -> {
            try(FileReader fileReader = new FileReader(groovyConfigFile)) {
                properties.load(fileReader);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        String value = properties.getProperty("run.test.points");
        String[] lines = value.split(";");
        List<InternalDataSourcePointCriteria> points = new ArrayList<>();
        for(String line: lines) {
            String[] columns = line.split(":");
            String first = columns[0];
            String typeName = columns[1];
            points.add(InternalDataSourcePointCriteria.criteria(dataSourceCriteria, InternalDataPointCriteria.point(first, InternalDataPointAttributeType.getType(typeName))));
        }

        JmxDataSourceCriteria jmxDataSourceCriteria = JmxDataSourceCriteria.scadaPerfMonitor();
        JmxDataSourcePointCriteria jmxDataSourcePointCriteria = JmxDataSourcePointCriteria.jdbcActive(jmxDataSourceCriteria, "DP_490967");

        List<DataSourcePointCriteria<?,?>> pointsToWatchLists = new ArrayList<>();
        pointsToWatchLists.add(jmxDataSourcePointCriteria);
        pointsToWatchLists.addAll(points);

        WatchListCriteria watchListCriteria = WatchListCriteria.criteria(new WatchListIdentifier("Scada perf monitor"),
                pointsToWatchLists.toArray(DataSourcePointCriteria<?,?>[]::new)
        );

        InternalDataSourcePointObjectsCreator dataSourcePointObjectsCreator = new InternalDataSourcePointObjectsCreator(navigationPage,
                points.toArray(InternalDataSourcePointCriteria[]::new)
        );
        dataSourcePointObjectsCreator.createObjects();

        JmxDataSourcePointObjectsCreator jmxDataSourcePointObjectsCreator = new JmxDataSourcePointObjectsCreator(navigationPage, jmxDataSourcePointCriteria);
        jmxDataSourcePointObjectsCreator.createObjects();

        WatchListObjectsCreator watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        watchListObjectsCreator.createObjects();

        try (CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {

            criteriaRegister.register(UpdateDataSourceCriteria.class, dataSourceCriteria);

            for(InternalDataSourcePointCriteria sourcePointCriteria: points) {
                criteriaRegister.register(InternalDataPointCriteria.class, sourcePointCriteria.getDataPoint());
            }

            for(InternalDataSourcePointCriteria sourcePointCriteria: points) {
                criteriaRegister.register(InternalDataSourcePointCriteria.class, sourcePointCriteria);
            }

            criteriaRegister.register(WatchListCriteria.class, watchListCriteria);

        }
    }

    @Override
    public Class<DataPointDetailsCheckTestsSuite> getClassTest() {
        return DataPointDetailsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
