package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.service.core.services.E2eResponse;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerParams;
import org.scadalts.e2e.service.impl.services.eventHandler.EventHandlerResponse;

public class EventHandlerUtil {

    private static EventHandlerResponse createEmailEventHandler(String xid){
        EventHandlerResponse body = EventHandlerResponse.builder()
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

        return body;
    }

    private static EventHandlerResponse createSmsEventHandler(String xid){
        EventHandlerResponse body = EventHandlerResponse.builder()
                .id(-1)
                .xid(xid)
                .alias("eventhandler_sms_test_create")
                .disabled(false)
                .activeRecipients(null)
                .build();

        return body;
    }

    public static E2eResponse<EventHandlerResponse> createEventHandler(String xid, int dataPointId, int eventDetectorId, int handlerType){

        EventHandlerParams eventHandlerParams = new EventHandlerParams();
        eventHandlerParams.setTypeId(1);
        eventHandlerParams.setTypeRef1(dataPointId);
        eventHandlerParams.setTypeRef2(eventDetectorId);
        eventHandlerParams.setHandlerType(handlerType);

        EventHandlerResponse body;

        switch (handlerType){
            case 2:
                body = createEmailEventHandler(xid);
                break;
            case 5:
                body = createSmsEventHandler(xid);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + handlerType);
        }

        eventHandlerParams.setBody(body);

        return TestWithoutPageUtil.createEventHandler(eventHandlerParams);
    }
}
