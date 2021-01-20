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
public class EventHandlerGetParams implements WebServiceObjectParams {
    private String xid;
}
