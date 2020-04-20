package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChartRenderer {

    private String type = "";
    private int numberOfPeriods = 0;
    private String timePeriodType = "";
    private boolean includeSum = false;
    private int limit = 0;
}
