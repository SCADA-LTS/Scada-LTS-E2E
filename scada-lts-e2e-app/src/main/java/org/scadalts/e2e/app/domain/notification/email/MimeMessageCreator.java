package org.scadalts.e2e.app.domain.notification.email;

import org.scadalts.e2e.common.utils.FileUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
class MimeMessageCreator {

    private final MessageTransformator transformator;
    private static File LOGO = FileUtil.getFile("templates/logo.png");
    private static File LOGS = FileUtil.getFile("e2e");

    MimeMessageCreator(MessageTransformator transformator) {
        this.transformator = transformator;
    }

    MimeMessage create(EmailData emailData, JavaMailSender javaMailSender) throws MessagingException {
        MimeMessage mail = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setBcc(emailData.getTo());
        helper.setFrom(emailData.getFrom());
        helper.setSubject(emailData.getTitle());
        helper.setText(transformator.transform(emailData, LOGO), transformator.isHtml());
        helper.addInline(LOGO.getName(), LOGO);
        helper.addAttachment(LOGS.getName(), LOGS);
        return mail;
    }
}
