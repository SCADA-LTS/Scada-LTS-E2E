package org.scadalts.e2e.test.impl.backstop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class BackstopPath {
    private String key;
    private String value;

    public BackstopPath(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
