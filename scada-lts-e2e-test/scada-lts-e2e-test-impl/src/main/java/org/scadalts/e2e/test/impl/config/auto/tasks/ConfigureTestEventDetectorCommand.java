package org.scadalts.e2e.test.impl.config.auto.tasks;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.CreateEventDetectorSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.CreateEventHandlerSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.CreateOneDataSourceTwoPointsSubCommand;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.CreateScriptSubCommand;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

@Data
public class ConfigureTestEventDetectorCommand implements Command<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        Xid dataPointToChangeXid = new Xid(TestImplConfiguration.dataPointToChangeXid);
        Xid dataPointToReadXid = new Xid(TestImplConfiguration.dataPointToReadXid);

        DataPointCriteria dataPointToChange = DataPointCriteria.numericNoChange(dataPointToChangeXid,
                new DataPointIdentifier("datapoint_to_change"), 123);

        DataPointCriteria dataPointToRead = DataPointCriteria.numericNoChange(dataPointToReadXid,
                new DataPointIdentifier("datapoint_to_read"), 12345);

        DataSourceIdentifier identifierForEventDetectorTest = new DataSourceIdentifier("datasource_event_detector_test");

        CreateOneDataSourceTwoPointsSubCommand createOneDataSourceTwoPointsSubTask = CreateOneDataSourceTwoPointsSubCommand.builder()
                .dataPoint1(dataPointToChange)
                .dataPoint2(dataPointToRead)
                .dataSourceIdentifier(identifierForEventDetectorTest)
                .navigationPage(navigationPage)
                .build();

        DataSourceCriteria dataSourceCriteria = createOneDataSourceTwoPointsSubTask.execute();

        CreateScriptSubCommand createScriptSubTask = CreateScriptSubCommand.builder()
                .dataPointCriteria(dataPointToChange)
                .navigationPage(navigationPage)
                .build();

        ScriptCriteria scriptCriteria = createScriptSubTask.execute();

        CreateEventDetectorSubCommand createEventDetectorSubTask = CreateEventDetectorSubCommand.builder()
                .dataPointCriteria(dataPointToChange)
                .dataSourceCriteria(dataSourceCriteria)
                .navigationPage(navigationPage)
                .build();

        EventDetectorCriteria eventDetectorCriteria = createEventDetectorSubTask.execute();

        CreateEventHandlerSubCommand createEventHandlerSubTask = CreateEventHandlerSubCommand.builder()
                .navigationPage(navigationPage)
                .eventDetectorCriteria(eventDetectorCriteria)
                .scriptCriteria(scriptCriteria)
                .build();

        EventHandlerCriteria eventHandlerCriteria = createEventHandlerSubTask.execute();

        CriteriaRegister creatorObjectRegister = new CriteriaRegister();
        creatorObjectRegister.register(DataSourceCriteria.class, dataSourceCriteria);
        creatorObjectRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToChange));
        creatorObjectRegister.register(DataSourcePointCriteria.class, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointToRead));
        creatorObjectRegister.register(EventHandlerCriteria.class, eventHandlerCriteria);
        creatorObjectRegister.register(EventDetectorCriteria.class, eventDetectorCriteria);
        creatorObjectRegister.register(ScriptCriteria.class, scriptCriteria);
        creatorObjectRegister.register(DataPointCriteria.class, dataPointToChange);
        creatorObjectRegister.register(DataPointCriteria.class, dataPointToRead);

        CriteriaRegisterAggregator creatorObjectByTestAggregator = CriteriaRegisterAggregator.INSTANCE;
        creatorObjectByTestAggregator.putRegister(getClassTarget(), creatorObjectRegister);
    }

    @Override
    public Class<EventDetectorCheckTest> getClassTarget() {
        return EventDetectorCheckTest.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
