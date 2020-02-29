package org.scadalts.e2e.service.impl.services.pointvalue;

import lombok.*;
import org.scadalts.e2e.service.impl.dicts.TextRendererType;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TextRenderer {

    private String colour;
    private String suffix;
    private TextRendererType typeName;
    private String metaText;
    private TextRendererDef def;

}
