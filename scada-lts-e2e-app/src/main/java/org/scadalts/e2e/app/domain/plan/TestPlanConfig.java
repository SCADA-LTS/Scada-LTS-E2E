package org.scadalts.e2e.app.domain.plan;

import org.scadalts.e2e.test.api.E2eTestApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestPlanConfig {

    @Bean
    E2eTestApi getTests() {
        return E2eTestApi.newInstance();
    }
}
