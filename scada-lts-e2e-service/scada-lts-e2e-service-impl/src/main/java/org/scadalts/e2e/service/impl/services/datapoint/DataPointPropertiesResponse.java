package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DataPointPropertiesResponse {

    private DataPointPropertiesJson dataPoints;

    public DataPointPropertiesResponse() {
        this.dataPoints = new DataPointPropertiesJson();
    }
}
