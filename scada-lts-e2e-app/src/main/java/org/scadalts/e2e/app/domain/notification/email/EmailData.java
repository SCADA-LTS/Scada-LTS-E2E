package org.scadalts.e2e.app.domain.notification.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConfig;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;

import java.io.File;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@Log4j2
class EmailData {

    private final String[] to;
    private final String content;
    private final String from;
    private final String header;
    private final String title;
    private final E2eSummarable summary;

    @Singular
    private final Set<File> attachments;

    @Singular
    private final Set<String> failTestNames;

    static EmailData create(E2eConfig config, E2eSummarable summary) {
        Set<String> failTestNames = summary.getFailTestNames();
        logger.info("failTestNames: {}", failTestNames);

        Set<File> attachments = _getAttachments(summary);
        logger.info("attachments: {}", attachments);

        return EmailData.builder()
                .content("")
                .title(config.getTitleEmail())
                .header("Scada-LTS-E2E")
                .from(config.getSendFrom())
                .to(config.getSendTo())
                .failTestNames(failTestNames)
                .attachments(attachments)
                .summary(summary)
                .build();
    }

    static EmailData create(E2eConfig config, String content, Throwable throwable) {

        return EmailData.builder()
                .content(content)
                .title(config.getTitleEmail())
                .header("Scada-LTS-E2E")
                .from(config.getSendFrom())
                .to(config.getSendTo())
                .failTestName(throwable.getClass().getName())
                .attachments(Collections.emptySet())
                .summary(E2eSummarable.empty())
                .build();
    }

    private static Set<File> _getAttachments(E2eSummarable summary) {
        return summary.getFailures().stream()
                .flatMap(a -> Stream.of(a.getScreenshotPng(), a.getSourcePageHtml())
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .collect(Collectors.toSet());
    }
}
