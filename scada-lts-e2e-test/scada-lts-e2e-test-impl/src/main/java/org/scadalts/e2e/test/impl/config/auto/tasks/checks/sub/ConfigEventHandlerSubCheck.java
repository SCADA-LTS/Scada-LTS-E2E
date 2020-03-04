package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.criterias.ScriptCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.EventHandlerIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;
import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EditEventHandlersPage;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.scadalts.e2e.test.core.asserts.E2eAssert.assertExists;

@Data
public class ConfigEventHandlerSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<EventHandlerCriteria> eventHandlerCriterias;

    @Override
    public void check() {
        EventHandlersPage eventHandlersPage = navigationPage.openEventHandlers();
        for(EventHandlerCriteria eventHandlerCriteria: eventHandlerCriterias) {
            EditEventHandlersPage editEventHandlersPage = eventHandlersPage.openEventHandlerEditor(eventHandlerCriteria);
            ScriptIdentifier activeScriptCommand = editEventHandlersPage.getActiveScriptCommand();
            ScriptIdentifier inactiveScriptCommand = editEventHandlersPage.getInactiveScriptCommand();
            EventHandlerType eventHandlerType = editEventHandlersPage.getEventHandlerType();
            boolean disabled = editEventHandlersPage.isDisabled();
            EventHandlerIdentifier eventHandlerIdentifier = editEventHandlersPage.getAlias();

            ScriptCriteria activeScript = eventHandlerCriteria.getActiveScript();
            ScriptCriteria inactiveScript = eventHandlerCriteria.getInactiveScript();

            assertExists(eventHandlersPage, eventHandlerCriteria);
            assertEquals(activeScript.getIdentifier(), activeScriptCommand);
            assertEquals(inactiveScript.getIdentifier(), inactiveScriptCommand);
            assertEquals(eventHandlerCriteria.isDisabled(), disabled);
            assertEquals(eventHandlerCriteria.getType(), eventHandlerType);
            assertEquals(eventHandlerCriteria.getIdentifier(), eventHandlerIdentifier);
        }
    }
}
