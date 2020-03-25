package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.utils.FileUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
@Log4j2
class MimeMessageCreator {

    private final MessageTransformator transformator;
    private static File LOGO = FileUtil.getFileFromJar("templates/logo.png");

    MimeMessageCreator(MessageTransformator transformator) {
        this.transformator = transformator;
    }

    MimeMessage create(EmailData emailData, JavaMailSender javaMailSender) throws MessagingException {
        logger.info("creating email...");
        MimeMessage mail = javaMailSender.createMimeMessage();
        _setMime(emailData, mail);
        return mail;
    }

    private void _setMime(EmailData emailData, MimeMessage mail) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setBcc(emailData.getTo());
        helper.setFrom(emailData.getFrom());
        helper.setSubject(emailData.getTitle());

        String content = transformator.transform(emailData, LOGO);
        helper.setText(content, transformator.isHtml());
        helper.addInline(LOGO.getName(), LOGO);

        File log = FileUtil.getFileFromFileSystem("e2e_log");
        helper.addAttachment(log.getName(), log);

        for (File attachment: emailData.getAttachments()) {
            if(attachment.exists()) {
                helper.addAttachment(attachment.getName(), attachment);
            }
        }
    }
}
