package org.scadalts.e2e.service.impl.services.storungs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class AcknowledgeResponse {

    private String id;
    @JsonProperty("request")
    private String requestStatus;
    private String error;
}
