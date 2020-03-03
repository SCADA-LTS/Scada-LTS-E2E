package org.scadalts.e2e.test.impl.config.auto.tasks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.creators.DataPointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.datapoint.DataPointDetailsCheckTestsSuite;

@Data
public class ConfigureTestDataPointDetailsCommand implements Command<DataPointDetailsCheckTestsSuite> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.virtualDataSourceSecond(new DataSourceIdentifier(TestImplConfiguration.dataSourceName));
        DataPointCriteria dataPointCriteria = DataPointCriteria.numericNoChange(new DataPointIdentifier(TestImplConfiguration.dataPointName));
        DataSourcePointCriteria dataSourcePointCriteria = DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria);

        CriteriaRegister creatorObjectRegister = new CriteriaRegister();
        creatorObjectRegister.register(DataSourceCriteria.class, dataSourceCriteria);
        creatorObjectRegister.register(DataPointCriteria.class, dataPointCriteria);
        creatorObjectRegister.register(DataSourcePointCriteria.class, dataSourcePointCriteria);

        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;
        creatorObjectByTestAggregator.putRegister(getClassTarget(), creatorObjectRegister);

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator = new DataSourcePointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataSourcePointObjectsCreator.createObjects();

        DataPointObjectsCreator dataPointObjectsCreator = new DataPointObjectsCreator(navigationPage, dataSourcePointCriteria);
        dataPointObjectsCreator.createObjects();

        WatchListObjectsCreator watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, dataSourcePointCriteria);
        watchListObjectsCreator.createObjects();
    }

    @Override
    public Class<DataPointDetailsCheckTestsSuite> getClassTarget() {
        return DataPointDetailsCheckTestsSuite.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
