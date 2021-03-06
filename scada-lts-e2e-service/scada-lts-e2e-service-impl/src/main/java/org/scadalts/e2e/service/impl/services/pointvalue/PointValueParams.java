package org.scadalts.e2e.service.impl.services.pointvalue;

import lombok.*;
import org.scadalts.e2e.service.core.services.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PointValueParams implements WebServiceObjectParams {
    private String xid;
}
