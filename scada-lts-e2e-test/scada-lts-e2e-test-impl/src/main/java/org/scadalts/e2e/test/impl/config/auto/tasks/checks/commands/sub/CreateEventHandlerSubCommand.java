package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.EventDetectorCriteria;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.EventHandlerObjectsCreator;

@Data
@Builder
public class CreateEventHandlerSubCommand implements SubCommand<EventHandlerCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull EventDetectorCriteria eventDetectorCriteria;
    private final @NonNull ScriptCriteria scriptCriteria;

    private CreateEventHandlerSubCommand(@NonNull NavigationPage navigationPage,
                                         @NonNull EventDetectorCriteria eventDetectorCriteria,
                                         @NonNull ScriptCriteria scriptCriteria) {
        this.navigationPage = navigationPage;
        this.eventDetectorCriteria = eventDetectorCriteria;
        this.scriptCriteria = scriptCriteria;
    }

    @Override
    public EventHandlerCriteria execute() {

        EventHandlerCriteria eventHandlerCriteria = EventHandlerCriteria.script(new EventHandlerIdentifier("eh_event_detector_test"),
                eventDetectorCriteria, scriptCriteria);

        EventHandlerObjectsCreator eventHandlerObjectsCreator = new EventHandlerObjectsCreator(navigationPage, eventHandlerCriteria);
        eventHandlerObjectsCreator.createObjects();

        return eventHandlerCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
