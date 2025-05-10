package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.VirtualDataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;

@Data
@Builder
public class CreateEventDetectorSubCommand implements SubCommand<EventDetectorCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull UpdateDataSourceCriteria dataSourceCriteria;
    private final @NonNull VirtualDataPointCriteria dataPointCriteria;
    private final @NonNull EventDetectorIdentifier eventDetectorIdentifier;
    private final @NonNull AlarmLevel alarmLevel;

    @Override
    public EventDetectorCriteria execute() {

        EventDetectorCriteria eventDetectorCriteria = EventDetectorCriteria.criteria(eventDetectorIdentifier, alarmLevel,
                VirtualDataSourcePointCriteria.virtualCriteria(dataSourceCriteria, dataPointCriteria));
        EventDetectorObjectsCreator eventDetectorObjectsCreator =
                new EventDetectorObjectsCreator(navigationPage, eventDetectorCriteria);
        eventDetectorObjectsCreator.createObjects();
        return eventDetectorCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
