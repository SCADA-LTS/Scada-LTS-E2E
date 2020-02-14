package org.scadalts.e2e.app.domain.notification.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.scadalts.e2e.test.core.plan.runner.E2eResultSummary;

import java.util.Set;

@Builder
@Getter
@ToString
class EmailData {

    private final String[] to;
    private final String content;
    private final String from;
    private final String header;
    private final String title;
    private final E2eResultSummary summary;

    @Singular
    private final Set<String> failTestNames;
}
