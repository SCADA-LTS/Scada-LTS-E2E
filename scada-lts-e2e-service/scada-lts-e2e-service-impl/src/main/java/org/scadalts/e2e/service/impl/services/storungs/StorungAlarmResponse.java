package org.scadalts.e2e.service.impl.services.storungs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StorungAlarmResponse {

    private String id;
    @JsonProperty("activation-time")
    private String activationTime;
    @JsonProperty("inactivation-time")
    private String inactivationTime;
    private String name;
    private int level;

}
