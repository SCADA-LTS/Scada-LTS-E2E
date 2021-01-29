package org.scadalts.e2e.service.impl.services.storungs;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class StorungAlarmParams {

    private PaginationParams paginationParams;
    private String dateDay;
    private String filter;
}
