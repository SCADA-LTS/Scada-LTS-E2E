package org.scadalts.e2e.service.impl.services.eventHandler;

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
public class EventHandlerParams implements WebServiceObjectParams {
    private String xid;
    private int typeId;
    private int typeRef1;
    private int typeRef2;
}
