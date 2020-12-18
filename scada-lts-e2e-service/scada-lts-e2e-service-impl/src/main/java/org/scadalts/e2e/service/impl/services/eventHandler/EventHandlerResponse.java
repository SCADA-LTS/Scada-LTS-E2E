package org.scadalts.e2e.service.impl.services.eventHandler;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventHandlerResponse {
    private int id;
    private String xid;
    private String alias;
    private int handlerType;
    private boolean disabled;
}
