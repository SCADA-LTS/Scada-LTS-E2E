package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
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

    private String xid;
    private String loggingType;
    private String intervalLoggingPeriodType;
    private String intervalLoggingType;
    private String purgeType;
    private PointLocator pointLocator;
    private List<?> eventDetectors;
    private String engineeringUnits;
    private String chartColour;
    private ChartRenderer chartRenderer;
    private String dataSourceXid;
    private int defaultCacheSize;
    private String deviceName;
    private boolean discardExtremeValues;
    private String discardHighLimit;
    private String discardLowLimit;
    private boolean enabled;
    private int intervalLoggingPeriod;
    private String name;
    private int purgePeriod;
    private TextRenderer textRenderer;
    private String tolerance;


}
