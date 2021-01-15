package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import org.scadalts.e2e.page.impl.dicts.EventSourcesType;
import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerPostParams;
import org.scadalts.e2e.service.impl.services.eventhandler.EventHandlerResponse;

public class EventHandlerUtil {

    private static EventHandlerResponse createEmailEventHandlerBody(String xid){

        return EventHandlerResponse.builder()
                .id(-1)
                .xid(xid)
                .alias("eventhandler_email_test_create")
                .disabled(false)
                .activeRecipients(null)
                .sendEscalation(false)
                .escalationDelayType(1)
                .escalationDelay(0)
                .escalationRecipients(null)
                .sendInactive(false)
                .inactiveOverride(false)
                .inactiveRecipients(null)
                .build();
    }

    private static EventHandlerResponse createSmsEventHandlerBody(String xid){

        return EventHandlerResponse.builder()
                .id(-1)
                .xid(xid)
                .alias("eventhandler_sms_test_create")
                .disabled(false)
                .activeRecipients(null)
                .build();
    }

    public static E2eResponse<EventHandlerResponse> createEventHandler(String xid, int dataPointId, int eventDetectorId, EventHandlerType handlerType){

        EventHandlerPostParams eventHandlerPostParams = new EventHandlerPostParams();
        eventHandlerPostParams.setTypeId(EventSourcesType.DATA_POINT);
        eventHandlerPostParams.setTypeRef1(dataPointId);
        eventHandlerPostParams.setTypeRef2(eventDetectorId);
        eventHandlerPostParams.setHandlerType(handlerType);

        EventHandlerResponse body;

        switch (handlerType){
            case EMAIL:
                body = createEmailEventHandlerBody(xid);
                break;
            case SMS:
                body = createSmsEventHandlerBody(xid);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + handlerType);
        }

        eventHandlerPostParams.setBody(body);

        return TestWithoutPageUtil.createEventHandler(eventHandlerPostParams);
    }
}
