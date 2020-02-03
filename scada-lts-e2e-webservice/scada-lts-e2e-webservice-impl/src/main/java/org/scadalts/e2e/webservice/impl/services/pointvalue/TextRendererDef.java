package org.scadalts.e2e.webservice.impl.services.pointvalue;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class TextRendererDef {

    private long id;
    private String name;
    private String exportName;
    private String nameKey;
    private Integer[] supportedDataTypes;

}
