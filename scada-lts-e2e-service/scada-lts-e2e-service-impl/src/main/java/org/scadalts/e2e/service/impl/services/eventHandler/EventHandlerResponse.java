package org.scadalts.e2e.service.impl.services.eventHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHandlerResponse implements Serializable {
    // Common fields
    private int id;
    private String xid;
    private String alias;
    private int handlerType;
    private boolean disabled;

    // Set point handler fields.
    private int targetPointId;
    private int activeAction;
    private String activeValueToSet;
    private int activePointId;
    private int inactiveAction;
    private String inactiveValueToSet;
    private int inactivePointId;

    // Email handler fields.
    private List<EventHandlerRecipients> activeRecipients;
    private boolean sendEscalation;
    private int escalationDelayType;
    private int escalationDelay;
    private List<EventHandlerRecipients> escalationRecipients;
    private boolean sendInactive;
    private boolean inactiveOverride;
    private List<EventHandlerRecipients> inactiveRecipients;

    // Process handler fields.
    private String activeProcessCommand;
    private String inactiveProcessCommand;

    // script fields
    private int activeScriptCommand;
    private int inactiveScriptCommand;
}
