package org.scadalts.e2e.service.impl.services.alarms;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {

    private String id;
    private Date activationTime;
    private Date inactivationTime;
    private String name;
    private String level;

}
