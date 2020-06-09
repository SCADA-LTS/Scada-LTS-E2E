package org.scadalts.e2e.service.impl.services.alarms;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PaginationParams {

    private int offset;
    private int limit;
}
