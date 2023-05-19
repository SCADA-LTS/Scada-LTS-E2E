package org.scadalts.e2e.app.domain.notification.send.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.domain.notification.send.MsgData;
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
    public String transform(MsgData msgData, File inline) {
        return templateEngine.process(templateName, _newContext(msgData, inline));
    }

    @Override
    public String transform(MsgData msgData) {
        return templateEngine.process(templateName, _newContext(msgData, null));
    }

    @Override
    public boolean isHtml() {
        return true;
    }

    private static Context _newContext(MsgData msgData, File inline) {
        Context context = new Context();
        long runTime = msgData.getSummary().getRunTime();
        String runtimeFormatted = ValueTimeUnitToPrint.preparingToPrintMs(runTime);
        long executeTime = msgData.getSummary().getExecuteTime();
        String executeTimeFormatted = ValueTimeUnitToPrint.preparingToPrintMs(executeTime);
        SendTo sendTo = msgData.getSendTo();

        _i18n(sendTo.getLocale(), context, msgData.getSummary().getStatusesLegend());

        context.setVariable("content", msgData.getContent());
        context.setVariable("header", msgData.getHeader());
        context.setVariable("title", msgData.getTitle());
        context.setVariable("summary", msgData.getSummary());
        context.setVariable("runtimeFormatted", runtimeFormatted);
        context.setVariable("executeTimeFormatted", executeTimeFormatted);
        context.setVariable("failTestNames", msgData.getFailTestNames());
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
        context.setVariable("testExecuteTimeTh", message.getString("e2e.test.test-execute-time"));
        context.setVariable("testRunTimeTh", message.getString("e2e.test.test-run-time"));
        context.setVariable("testExecuteTimeTh", message.getString("e2e.test.test-execute-time"));

        context.setVariable("testIgnoredTh", message.getString("e2e.test.ignored"));
        context.setVariable("testFailedTh", message.getString("e2e.test.failed"));
        context.setVariable("testRuntimeTh", message.getString("e2e.test.runtime"));
        context.setVariable("testGoToPageTh", message.getString("e2e.test.go-to-page"));
        context.setVariable("startDateTimeTh", message.getString("e2e.test.start-date-time"));
        context.setVariable("endDateTimeTh", message.getString("e2e.test.end-date-time"));

        context.setVariable("legendTh", translate(legends, message));
    }

    private static Map<String, String> translate(Map<String, String> legends, ResourceBundle message) {
        Map<String, String> translated = new HashMap<>();
        for (String key: legends.keySet()) {
            translated.put(key, message.getString(legends.get(key)));
        }
        return translated;
    }
}
