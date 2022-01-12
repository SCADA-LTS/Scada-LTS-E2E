package org.scadalts.e2e.test.impl.backstop;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.*;

@Data
@JsonIgnoreProperties
public class BackstopConfig {

    private String id = "backstop_default";
    private List<Viewports> viewports = Arrays.asList(new Viewports());
    private String onBeforeScript = "puppet/onBefore.js";
    private String onReadyScript = "puppet/onReady.js";
    private List<Scenario> scenarios = new ArrayList<>();
    private Map<String, String> paths;
    private List<String> report = Arrays.asList("browser", "json");
    private String engine = "puppeteer";
    private EngineOptions engineOptions = new EngineOptions();
    private int asyncCaptureLimit = 1;
    private int asyncCompareLimit = 15;
    private boolean debug = false;
    private boolean debugWindow = false;

    public BackstopConfig() {
        paths = new LinkedHashMap<>();
        paths.put("bitmaps_reference", "backstop_data/bitmaps_reference");
        paths.put("bitmaps_test", "backstop_data/bitmaps_test");
        paths.put("engine_scripts", "backstop_data/engine_scripts");
        paths.put("html_report", "backstop_data/html_report");
        paths.put("ci_report", "backstop_data/ci_report");
    }

    public void addScenario(Scenario scenarios) {
        this.scenarios.add(scenarios);
    }
}
