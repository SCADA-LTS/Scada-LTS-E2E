package org.scadalts.e2e.webservice.core.services;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Data
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class E2eResponse<T> {

    private int status;
    private T value;
    private String msg;
    private String error;
    private Map<String, Object> headers;
    private String sessionId;
    private String mediaType;

}
