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
public class TextRenderer {

    private @NonNull String type;
    private @NonNull String format;
    private @NonNull String suffix;
    private @NonNull int conversionExponent;
    private @Singular @NonNull List<RangeValueJson> rangeValues;

}
