package org.scadalts.e2e.service.impl.services.eventhandler;

import lombok.*;
import org.scadalts.e2e.service.core.services.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventHandlerPostParams implements WebServiceObjectParams {
    private String typeId;
    private int typeRef1;
    private int typeRef2;
    private String handlerType;
    private EventHandlerResponse body;
}
