package org.scadalts.e2e.webservice.impl.services.pointvalue;

import lombok.*;
import org.scadalts.e2e.webservice.impl.dict.DataPointRestType;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PointValueResponse {

    private String value;
    private String formattedValue;
    private long ts;
    private String name;
    private String xid;
    private DataPointRestType type;
    private String chartColour;
    private TextRenderer textRenderer;

}
