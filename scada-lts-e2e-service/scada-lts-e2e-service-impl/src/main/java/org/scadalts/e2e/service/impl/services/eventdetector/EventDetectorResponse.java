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
    @Builder.Default
    private int detectorType = 1;
    @Builder.Default
    private int alarmLevel = 1;
    private double limit;
    @Builder.Default
    private int duration = 1;
    @Builder.Default
    private int durationType = 1;
    private boolean binaryState;
    private int multistateState;
    @Builder.Default
    private int changeCount = 2;
    private String alphanumericState;
    private double weight;
}
