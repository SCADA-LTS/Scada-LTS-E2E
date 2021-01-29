package org.scadalts.e2e.service.impl.services.eventdetector;

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
public class EventDetectorParams implements WebServiceObjectParams {
    private int id;
    private String xid;
    private EventDetectorResponse body;
}
