package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.SendTo;
import org.scadalts.e2e.common.core.measure.ValueTimeUnitToPrint;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.*;

@Log4j2
class ThymeleafMessageTransformator implements MessageTransformator {

    private final TemplateEngine templateEngine;
    private final String templateName;

    ThymeleafMessageTransformator(TemplateEngine templateEngine, String templateName) {
        this.templateEngine = templateEngine;
        this.templateName = templateName;
    }

    @Override
    public String transform(EmailData emailData, File inline) {
        return templateEngine.process(templateName, _newContext(emailData, inline));
    }

    @Override
    public String transform(EmailData emailData) {
        return templateEngine.process(templateName, _newContext(emailData, null));
    }

    @Override
    public boolean isHtml() {
        return true;
    }

    private static Context _newContext(EmailData emailData, File inline) {
        Context context = new Context();
        long runtime = emailData.getSummary().getRunTime();
        String runtimeFormatted = ValueTimeUnitToPrint.preparingToPrintMs(runtime);
        SendTo sendTo = emailData.getSendTo();

        _i18n(sendTo.getLocale(), context, emailData.getSummary().getStatusesLegend());

        context.setVariable("content", emailData.getContent());
        context.setVariable("header", emailData.getHeader());
        context.setVariable("title", emailData.getTitle());
        context.setVariable("summary", emailData.getSummary());
        context.setVariable("runtimeFormatted", runtimeFormatted);
        context.setVariable("failTestNames", emailData.getFailTestNames());
        if(inline != null)
            context.setVariable(inline.getName(), inline.getName());
        return context;
    }

    private static void _i18n(Locale locale, Context context, Map<String, String> legends) {
        ResourceBundle message = ResourceBundle.getBundle("lang", locale);

        context.setVariable("testNameTh", message.getString("e2e.test.name"));
        context.setVariable("statusTh", message.getString("e2e.test.status"));
        context.setVariable("testMethodNameTh", message.getString("e2e.test.method-name"));
        context.setVariable("testMessageTh", message.getString("e2e.test.message"));
        context.setVariable("testSessionIdTh", message.getString("e2e.test.session-id"));
        context.setVariable("testSummaryTh", message.getString("e2e.test.summary"));
        context.setVariable("testRunsTh", message.getString("e2e.test.run"));

        context.setVariable("testIgnoredTh", message.getString("e2e.test.ignored"));
        context.setVariable("testFailedTh", message.getString("e2e.test.failed"));
        context.setVariable("testRuntimeTh", message.getString("e2e.test.runtime"));
        context.setVariable("testGoToPageTh", message.getString("e2e.test.go-to-page"));
        context.setVariable("hasOccurredTh", message.getString("e2e.test.start-date-time"));

        context.setVariable("legendTh", translate(legends, message));
    }

    private static Map<String, String> translate(Map<String, String> legends, ResourceBundle message) {
        Map<String, String> translated = new HashMap<>();
        for (String key: legends.keySet()) {
            translated.put(key , message.getString(legends.get(key)));
        }
        return translated;
    }
}
