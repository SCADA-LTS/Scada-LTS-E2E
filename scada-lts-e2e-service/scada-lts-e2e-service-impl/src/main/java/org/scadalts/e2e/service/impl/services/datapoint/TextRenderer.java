package org.scadalts.e2e.service.impl.services.datapoint;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TextRenderer {

    private @NonNull String type = "";
    private @NonNull String typeName = "";
    private @NonNull String format = "";
    private @NonNull String suffix = "";
    private @NonNull int conversionExponent = 0;
    private @Singular @NonNull List<RangeValueJson> rangeValues = new ArrayList<>();
    private Map<String, Object> def = new HashMap<>();

}
