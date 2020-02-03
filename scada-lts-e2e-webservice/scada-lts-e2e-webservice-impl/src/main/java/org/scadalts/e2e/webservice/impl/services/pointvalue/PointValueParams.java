package org.scadalts.e2e.webservice.impl.services.pointvalue;

import lombok.*;
import org.scadalts.e2e.webservice.core.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PointValueParams implements WebServiceObjectParams {
    private String xid;
}
