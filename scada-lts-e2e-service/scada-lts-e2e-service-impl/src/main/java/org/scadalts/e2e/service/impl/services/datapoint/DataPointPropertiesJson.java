package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

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
    private String loggingType = "";
    private String intervalLoggingPeriodType = "";
    private String intervalLoggingType = "";
    private String purgeType = "";
    private PointLocator pointLocator = new PointLocator();
    private List<String> eventDetectors = new ArrayList<>();
    private String engineeringUnits = "";
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


}
