package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangeJson {

    private String type = "";
    private String startValue = "";

}
