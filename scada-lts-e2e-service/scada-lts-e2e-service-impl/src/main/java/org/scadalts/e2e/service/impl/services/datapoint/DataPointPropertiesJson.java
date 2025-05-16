package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPointPropertiesJson {

    private String xid = "";
    private int loggingType = -1;
    private int intervalLoggingPeriodType = -1;
    private int intervalLoggingType = -1;
    private int purgeType = -1;
    private PointLocator pointLocator = new PointLocator();
    private List<EventDetectorJson> eventDetectors = new ArrayList<>();
    private int engineeringUnits = -1;
    private String chartColour = "";
    private ChartRenderer chartRenderer = new ChartRenderer();
    private String dataSourceXid = "";
    private int defaultCacheSize = 0;
    private String deviceName = "";
    private boolean discardExtremeValues = false;
    private String discardHighLimit = "";
    private String discardLowLimit = "";
    private boolean enabled = false;
    private int intervalLoggingPeriod = 0;
    private String name = "";
    private int purgePeriod = 0;
    private TextRenderer textRenderer = new TextRenderer();
    private String tolerance = "";

    private Map<String, Object> def = new HashMap<>();


}
