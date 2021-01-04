package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.common.exceptions.ConfigureTestException;
import org.scadalts.e2e.common.utils.ExecutorUtil;
import org.scadalts.e2e.page.impl.criterias.*;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegister;
import org.scadalts.e2e.test.impl.config.auto.registers.CriteriaRegisterAggregator;
import org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub.*;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;
import org.scadalts.e2e.test.impl.tests.check.eventdetectors.EventDetectorCheckTest;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class ConfigureSmsHandlerTestCommand implements Command<EventDetectorCheckTest> {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        ExecutorUtil.execute(this::_execute,
                CriteriaRegisterAggregator.INSTANCE::removeRegister,
                getClassTest(), ConfigureTestException::new);
    }

    private void _execute() {

        DataSourceIdentifier dataSourceIdentifier = new DataSourceIdentifier("ds",
                DataSourceType.VIRTUAL_DATA_SOURCE);
        DataSourceCriteria dataSourceCriteria = DataSourceCriteria.criteriaSecond(dataSourceIdentifier);

        CreateEventDetectorSubCommand[] commands = new CreateEventDetectorSubCommand[]{
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp AL with_ed_CRITICAL", DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_CRITICAL_for_AL",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.CRITICAL)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp ST with_ed_CRITICAL",
                                                DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_CRITICAL_for_ST",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.CRITICAL)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp AL with_ed_URGENT",
                                                DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_URGENT_for_AL",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.URGENT)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp ST with_ed_URGENT",
                                                DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_URGENT_for_ST",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.URGENT)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp AL with_ed_INFORMATION",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_INFORMATION_for_AL",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.INFORMATION)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp ST with_ed_INFORMATION",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_INFORMATION_for_ST",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.INFORMATION)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp AL with_ed_LIFE_SAFETY",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_LIFE_SAFETY_for_AL",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.LIFE_SAFETY)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp ST with_ed_LIFE_SAFETY",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_LIFE_SAFETY_for_ST",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.LIFE_SAFETY)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp AL with_ed_NONE",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_NONE_AL",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.NONE)
                        .navigationPage(navigationPage)
                        .build(),
                CreateEventDetectorSubCommand.builder()
                        .dataPointCriteria(DataPointCriteria
                                .noChange(new DataPointIdentifier("dp ST with_ed_NONE",
                                        DataPointType.BINARY), "0")
                        )
                        .dataSourceCriteria(dataSourceCriteria)
                        .eventDetectorIdentifier(
                                new EventDetectorIdentifier("ed_change_NONE_ST",
                                        EventDetectorType.CHANGE))
                        .alarmLevel(AlarmLevel.NONE)
                        .navigationPage(navigationPage)
                        .build()
        };

        Set<DataPointCriteria> dataPoints = Stream.of(commands)
                .map(CreateEventDetectorSubCommand::getDataPointCriteria)
                .collect(Collectors.toSet());

        DataSourcePointObjectsCreator dataSourcePointObjectsCreator =
                new DataSourcePointObjectsCreator(navigationPage, dataSourceCriteria, dataPoints.toArray(new DataPointCriteria[]{}));
        dataSourcePointObjectsCreator.createObjects();

        Set<EventDetectorCriteria> eventDetectorCriterias = Stream.of(commands)
                .map(CreateEventDetectorSubCommand::execute)
                .collect(Collectors.toSet());

        Set<DataSourcePointCriteria> dataSourcePointCriterias = eventDetectorCriterias.stream()
                .map(EventDetectorCriteria::getDataSourcePointCriteria)
                .collect(Collectors.toSet());

        try(CriteriaRegister criteriaRegister = new CriteriaRegister(getClassTest())) {
            criteriaRegister.register(DataSourceCriteria.class, dataSourceCriteria);
            criteriaRegister.register(DataSourcePointCriteria.class, dataSourcePointCriterias);
            criteriaRegister.register(DataPointCriteria.class, dataPoints);
            criteriaRegister.register(EventDetectorCriteria.class, eventDetectorCriterias);
        }
    }

    @Override
    public Class<EventDetectorCheckTest> getClassTest() {
        return EventDetectorCheckTest.class;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
