package org.scadalts.e2e.test.impl.backstop;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class Scenario {

    private String label;
    private String cookiePath = "backstop_data/engine_scripts/cookies.json";
    private String url;
    private int delay = 5000;
    private int expect = 0;
    private double misMatchThreshold = 0.1;
    private boolean requireSameDimensions = true;
    private List<String> selectors = Arrays.asList("#viewContainer");

    public Scenario(String label, String url) {
        this.label = label;
        this.url = url;
    }
}