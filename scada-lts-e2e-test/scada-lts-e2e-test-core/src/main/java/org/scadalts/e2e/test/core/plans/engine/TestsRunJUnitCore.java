package org.scadalts.e2e.test.core.plans.engine;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;
import org.scadalts.e2e.common.core.config.E2eConfiguration;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
class TestsRunJUnitCore implements TestsRunEngine {

    private final JUnitCore jUnitCore;

    TestsRunJUnitCore(JUnitCore jUnitCore) {
        this.jUnitCore = jUnitCore;
    }

    @Override
    public Map<Class<?>, List<E2eResult>> run(List<Class<?>> tests) {
        Map<Class<?>, List<E2eResult>> results = new HashMap<>();

        for (Class<?> test: tests) {
            results.putIfAbsent(test, new ArrayList<>());
            E2eResult result = _run(test, new E2eRunListener(test));
            results.get(test).add(result);
            if(!result.wasSuccessful()) {
                logger.info("repeats test...{}", test.getSimpleName());
                result = _run(test, new E2eRunListener(test));
                results.get(test).add(result);
            }
        }
        return results;
    }

    private E2eResult _run(Class<?> test, RunListener runListener) {
        jUnitCore.addListener(runListener);
        long start = System.currentTimeMillis();
        Result result = jUnitCore.run(test);
        long end = System.currentTimeMillis();
        jUnitCore.removeListener(runListener);
        return E2eResult.builder()
                .url(E2eConfiguration.baseUrl)
                .result(result)
                .sessionId(E2eConfiguration.sessionId)
                .simpleTestName(test.getSimpleName())
                .testName(test.getName())
                .startDateTime(Instant.ofEpochMilli(start).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .endDateTime(Instant.ofEpochMilli(end).atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}
