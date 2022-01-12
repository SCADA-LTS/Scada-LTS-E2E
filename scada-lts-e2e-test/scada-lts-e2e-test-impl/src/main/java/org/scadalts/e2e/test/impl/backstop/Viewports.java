package org.scadalts.e2e.test.impl.backstop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class Viewports {

    private String label = "FullHD PC";
    private int width = 1920;
    private int height = 1080;

}
