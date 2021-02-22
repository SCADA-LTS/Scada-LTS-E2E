package org.scadalts.e2e.service.impl.services.pointvalue;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.scadalts.e2e.service.core.services.GetFormattedValueResponse;
import org.scadalts.e2e.service.core.services.GetValueResponse;
import org.scadalts.e2e.service.core.services.ValueUnfromatted;

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
public class PointValueResponse implements GetValueResponse, GetFormattedValueResponse, ValueUnfromatted<PointValueResponse> {

    private String value;
    private String formattedValue;
    private long ts;
    private String name;
    private String xid;
    private String type;
    private String chartColour;
    private TextRendererJson textRenderer;

    @Override
    public PointValueResponse perform() {
        return PointValueResponse.builder()
                .xid(xid)
                .type(type)
                .ts(ts)
                .textRenderer(textRenderer)
                .name(name)
                .formattedValue(unformat(formattedValue))
                .value(unformat(value))
                .chartColour(chartColour)
                .build();
    }
}
