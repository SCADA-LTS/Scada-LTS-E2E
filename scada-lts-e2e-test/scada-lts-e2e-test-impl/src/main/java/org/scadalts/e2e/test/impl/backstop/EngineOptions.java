package org.scadalts.e2e.test.impl.backstop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@JsonIgnoreProperties
public class EngineOptions {
    private List<String> args = Arrays.asList("--no-sandbox");
}
