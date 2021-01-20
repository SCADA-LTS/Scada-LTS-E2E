package org.scadalts.e2e.service.impl.services.eventhandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHandlerRecipients {

    private int recipientType;
    private int referenceId;
    private String referenceAddress;
}
