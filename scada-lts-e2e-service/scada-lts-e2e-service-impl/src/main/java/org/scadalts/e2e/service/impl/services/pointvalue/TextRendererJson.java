package org.scadalts.e2e.service.impl.services.pointvalue;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TextRendererJson {

    private String colour;
    private String suffix;
    private String typeName;
    private String metaText;
    private TextRendererDef def;

}
