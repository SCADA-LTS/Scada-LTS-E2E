package org.scadalts.e2e.webservice.impl.services.cmp;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.webservice.core.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

@Log4j2
@Data
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//@JsonDeserialize(using = CmpParamsDeserializer.class)
public class CmpParams implements WebServiceObjectParams {

    private String xid;
    private String value;
    private String resultOperationSave;
    private String error;

}
