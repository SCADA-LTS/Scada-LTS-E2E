package org.scadalts.e2e.service.impl.services.eventdetector;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventDetectorPostResponse {

    private int id;
    private String xid;
    private String alias;
}
