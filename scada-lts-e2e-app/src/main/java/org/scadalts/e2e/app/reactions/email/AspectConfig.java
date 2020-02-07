package org.scadalts.e2e.app.reactions.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AspectConfig {

    @Bean
    SendEmailAspect emailAspect() {
        return new SendEmailAspect();
    }
}
