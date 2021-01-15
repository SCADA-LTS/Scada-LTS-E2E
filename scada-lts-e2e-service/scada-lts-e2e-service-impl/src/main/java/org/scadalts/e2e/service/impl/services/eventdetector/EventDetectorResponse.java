package org.scadalts.e2e.service.impl.services.eventdetector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetectorResponse implements Serializable {
    private int id;
    private String xid;
    private String alias;
    private int detectorType;
    private int alarmLevel;
    private double limit;
    private int duration;
    private int durationType;
    private boolean binaryState;
    private int multistateState;
    private int changeCount;
    private String alphanumericState;
    private double weight;
}
