package org.scadalts.e2e.app.schedulers;


import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.annotations.SendEmailReaction;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.api.E2eTestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@SendEmailReaction
@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "scheduling-mode")
public class TestPlanScheduler {

    private E2eConfig config;

    @Autowired
    public TestPlanScheduler(E2eConfig config) {
        this.config = config;
    }

    @Scheduled(cron = "${test.e2e.run-app.cron-pattern}")
    public boolean executeTest() {
        logger.info("run...");
        E2eTestApi tests = E2eTestApi.newInstance(config);
        return tests.run();
    }
}
