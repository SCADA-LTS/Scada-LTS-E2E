package org.scadalts.e2e.service.core.services;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Map;

@Getter
@Builder
@ToString
@XmlRootElement
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class E2eResponse<T> {

    private int status;
    private T value;
    private String valueFormatted;
    private String msg;
    private String error;
    private Map<String, Object> headers;
    private String sessionId;
    private String mediaType;

    public static <T> E2eResponse<T> empty() {
        return E2eResponse.<T>builder().status(-1).build();
    }

    public boolean isEmpty() {
        return status == -1;
    }

    public String getLocation() {
        if(headers == null || headers.isEmpty())
            return "";
        Object location = headers.get("Location");
        if(location == null)
            return "";
        return String.valueOf(location);
    }
}
