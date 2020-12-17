package org.scadalts.e2e.service.impl.services.eventDetector;

import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EventDetectorResponse {
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
