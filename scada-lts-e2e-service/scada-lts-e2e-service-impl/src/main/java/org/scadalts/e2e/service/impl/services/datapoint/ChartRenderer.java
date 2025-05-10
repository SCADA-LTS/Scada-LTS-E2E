package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

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
    private String typeName = "";
    private int numberOfPeriods = 0;
    private String timePeriodType = "";
    private boolean includeSum = false;
    private int limit = 0;
    private int timePeriod;
    private Map<String, Object> def = new HashMap<>();
}
