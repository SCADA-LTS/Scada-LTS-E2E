package org.scadalts.e2e.app.domain.plan;


import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.domain.notification.email.SendEmailReaction;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.api.E2eTestApi;
import org.scadalts.e2e.test.core.plan.runner.E2eResultSummary;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@Logging
@SendEmailReaction
@Component
@ConditionalOnProperty(prefix = "test.e2e.run-app", name = "continuous-mode")
public class TestPlanScheduler {

    private final E2eConfig config;

    public TestPlanScheduler(E2eConfig config) {
        this.config = config;
    }

    @Logging
    @Scheduled(cron = "${test.e2e.run-app.cron-pattern}")
    public E2eResultSummary executeTest() {
        E2eTestApi tests = E2eTestApi.newInstance();
        return tests.run(config);
    }
}
