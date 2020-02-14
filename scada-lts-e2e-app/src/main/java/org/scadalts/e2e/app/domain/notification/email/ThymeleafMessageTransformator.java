package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.common.measure.ValueTimeUnitToPrint;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

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
    public boolean isHtml() {
        return true;
    }

    private static Context _newContext(EmailData emailData, File inline) {
        Context context = new Context();
        long runtime = emailData.getSummary().getRunTime();
        String runtimeFormatted = ValueTimeUnitToPrint.preparingToPrintMs(runtime);
        context.setVariable("content", emailData.getContent());
        context.setVariable("header", emailData.getHeader());
        context.setVariable("title", emailData.getTitle());
        context.setVariable("summary", emailData.getSummary());
        context.setVariable("runtimeFormatted", runtimeFormatted);
        context.setVariable("failTestNames", emailData.getFailTestNames());
        context.setVariable(inline.getName(), inline.getName());
        return context;
    }
}
