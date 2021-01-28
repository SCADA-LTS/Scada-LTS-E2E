package org.scadalts.e2e.service.impl.services.eventhandler;

import org.scadalts.e2e.page.impl.dicts.EventHandlerType;
import lombok.*;
import org.scadalts.e2e.page.impl.dicts.EventSourcesType;
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
    private EventSourcesType typeId;
    private int typeRef1;
    private int typeRef2;
    private EventHandlerType handlerType;
    private EventHandlerResponse body;
}
