package org.scadalts.e2e.app.domain.notification.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.config.SendTo;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.scadalts.e2e.test.core.plans.engine.TestStatus;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Getter
@ToString
@Log4j2
class EmailData {

    private final SendTo sendTo;
    private final String content;
    private final String from;
    private final String header;
    private final String title;
    private final E2eSummarable summary;

    private final String testStatusesLegend;

    @Singular
    private final Set<File> attachments;

    @Singular
    private final List<String> failTestNames;

    static EmailData create(E2eConfig config, SendTo sendTo, E2eSummarable summary) {
        List<String> failTestNames = summary.getFailTestNames();
        logger.info("failTestNames: {}", failTestNames);

        Map<String, TestStatus> testStatuses = summary.getTestStatuses();
        logger.info("testStatuses: {}", testStatuses);

        Set<File> attachments = _getAttachments(summary);
        logger.info("attachments: {}", attachments);

        return EmailData.builder()
                .content("")
                .title(config.getTitleEmail())
                .header("Scada-LTS-E2E")
                .from(config.getSendFrom())
                .sendTo(sendTo)
                .failTestNames(failTestNames)
                .attachments(attachments)
                .summary(summary)
                .build();
    }

    static EmailData createSuccess(E2eConfig config, SendTo sendTo, E2eSummarable summary) {

        Map<String, TestStatus> testStatuses = summary.getTestStatuses();
        logger.info("testStatuses: {}", testStatuses);

        Set<File> attachments = _getAttachments(summary);
        logger.info("attachments: {}", attachments);

        return EmailData.builder()
                .content("")
                .title(config.getTitleEmailSuccess())
                .header("Scada-LTS-E2E")
                .from(config.getSendFrom())
                .sendTo(sendTo)
                .failTestNames(new ArrayList<>())
                .attachments(attachments)
                .summary(summary)
                .build();
    }

    static EmailData create(E2eConfig config, SendTo sendTo, String content, Throwable throwable) {

        return EmailData.builder()
                .content(content)
                .title(config.getTitleEmail())
                .header("Scada-LTS-E2E")
                .from(config.getSendFrom())
                .sendTo(sendTo)
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
