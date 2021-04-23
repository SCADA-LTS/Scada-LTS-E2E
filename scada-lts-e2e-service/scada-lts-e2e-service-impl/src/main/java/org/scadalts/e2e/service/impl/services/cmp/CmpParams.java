package org.scadalts.e2e.service.impl.services.cmp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.scadalts.e2e.service.core.services.GetValueResponse;
import org.scadalts.e2e.service.core.services.ValueUnfromatted;
import org.scadalts.e2e.service.core.services.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

import static org.scadalts.e2e.common.core.utils.FormatUtil.unformat;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CmpParams implements WebServiceObjectParams, GetValueResponse, ValueUnfromatted<CmpParams> {

    @JsonProperty("xid")
    private String dataPointXid;
    private String value;
    private String resultOperationSave;
    private String error;

    @Override
    public CmpParams perform() {
        return CmpParams.builder()
                .value(unformat(value))
                .dataPointXid(dataPointXid)
                .resultOperationSave(resultOperationSave)
                .error(error)
                .build();
    }
}
