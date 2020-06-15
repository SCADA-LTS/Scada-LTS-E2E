package org.scadalts.e2e.service.impl.services.alarms;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.jws.HandlerChain;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AlarmResponse {

    private String id;
    @JsonProperty("activation-time")
    private String activationTime;
    @JsonProperty("inactivation-time")
    private String inactivationTime;
    private String name;
    private String level;

}