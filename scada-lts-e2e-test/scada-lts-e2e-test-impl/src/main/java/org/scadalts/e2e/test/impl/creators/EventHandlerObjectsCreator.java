package org.scadalts.e2e.test.impl.creators;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.EventHandlerCriteria;
import org.scadalts.e2e.page.impl.pages.eventhandlers.EventHandlersPage;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.core.creators.CreatorObject;

@Log4j2
public class EventHandlerObjectsCreator implements CreatorObject<EventHandlersPage, EventHandlersPage> {

    private final NavigationPage navigationPage;
    private final EventHandlerCriteria[] eventHandlerCriterias;
    private EventHandlersPage eventHandlersPage;

    public EventHandlerObjectsCreator(NavigationPage navigationPage, EventHandlerCriteria... eventHandlerCriterias) {
        this.navigationPage = navigationPage;
        this.eventHandlerCriterias = eventHandlerCriterias;
    }

    @Override
    public EventHandlersPage deleteObjects() {
        EventHandlersPage eventHandlersPage = openPage();
        for (EventHandlerCriteria criteria: eventHandlerCriterias) {
            if(eventHandlersPage.containsObject(criteria)) {
                logger.info("delete object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getIdentifier().getType(), criteria.getXid().getValue(),
                        criteria.getClass().getSimpleName());
                eventHandlersPage
                        .openEventHandlerEditor(criteria)
                        .deleteEventHandler()
                        .reopen();
            }
        }
        return eventHandlersPage;
    }

    @Override
    public EventHandlersPage createObjects() {
        EventHandlersPage eventHandlersPage = openPage();
        for (EventHandlerCriteria criteria: eventHandlerCriterias) {
            if(!eventHandlersPage.containsObject(criteria)) {
                logger.info("create object: {}, type: {}, xid: {}, class: {}", criteria.getIdentifier().getValue(),
                        criteria.getIdentifier().getType(), criteria.getXid().getValue(),
                        criteria.getClass().getSimpleName());
                eventHandlersPage.openEventHandlerCreator(criteria.getEventDetectorCriteria())
                        .setEventHandlerType(criteria.getIdentifier().getType())
                        .setXid(criteria.getXid())
                        .setAlisas(criteria.getIdentifier())
                        .setDisabled(criteria.isDisabled())
                        .setActiveScriptCommand(criteria.getActiveScript())
                        .saveEventHandler()
                        .waitForObject(criteria.getIdentifier());
            }
            eventHandlersPage.reopen();
        }
        return eventHandlersPage;
    }

    @Override
    public EventHandlersPage openPage() {
        if(eventHandlersPage == null) {
            eventHandlersPage = navigationPage.openEventHandlers();
            return eventHandlersPage;
        }
        return eventHandlersPage.reopen();
    }
}
