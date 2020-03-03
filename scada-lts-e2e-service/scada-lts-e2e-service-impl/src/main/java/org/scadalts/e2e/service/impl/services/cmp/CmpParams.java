package org.scadalts.e2e.service.impl.services.cmp;

import lombok.*;
import org.scadalts.e2e.service.core.services.GetValueResponse;
import org.scadalts.e2e.service.core.services.ValueUnfromatted;
import org.scadalts.e2e.service.core.services.WebServiceObjectParams;

import javax.xml.bind.annotation.XmlRootElement;

import static org.scadalts.e2e.common.utils.FormatUtil.unformat;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CmpParams implements WebServiceObjectParams, GetValueResponse, ValueUnfromatted<CmpParams> {

    private String xid;
    private String value;
    private String resultOperationSave;
    private String error;

    @Override
    public CmpParams perform() {
        return CmpParams.builder()
                .value(unformat(value))
                .xid(xid)
                .resultOperationSave(resultOperationSave)
                .error(error)
                .build();
    }
}
