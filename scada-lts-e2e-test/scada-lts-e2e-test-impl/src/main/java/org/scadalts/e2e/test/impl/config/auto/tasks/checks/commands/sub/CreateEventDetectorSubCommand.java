package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourceCriteria;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventDetectorIdentifier;
import org.scadalts.e2e.page.impl.dicts.AlarmLevel;
import org.scadalts.e2e.page.impl.dicts.EventDetectorType;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.EventDetectorObjectsCreator;

@Data
@Builder
public class CreateEventDetectorSubCommand implements SubCommand<EventDetectorCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourceCriteria dataSourceCriteria;
    private final @NonNull DataPointCriteria dataPointCriteria;

    @Override
    public EventDetectorCriteria execute() {

        EventDetectorCriteria eventDetectorCriteria = EventDetectorCriteria
                .criteria(new EventDetectorIdentifier("ed_event_detector_test",EventDetectorType.CHANGE)
                        , AlarmLevel.NONE, DataSourcePointCriteria.criteria(dataSourceCriteria, dataPointCriteria));
        EventDetectorObjectsCreator eventDetectorObjectsCreator = new EventDetectorObjectsCreator(navigationPage,eventDetectorCriteria);
        eventDetectorObjectsCreator.createObjects();
        return eventDetectorCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
