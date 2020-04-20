package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.config.E2eConstant;
import org.scadalts.e2e.common.utils.FileUtil;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

import static org.scadalts.e2e.common.utils.ZipUtil.zip;

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
        _createMime(emailData, mail);
        return mail;
    }

    private void _createMime(EmailData emailData, MimeMessage mail) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mail, true, "UTF-8");
        helper.setBcc(emailData.getSendTo().getAdress());
        helper.setFrom(emailData.getFrom());
        helper.setSubject(emailData.getTitle());

        _generateContent(emailData, helper);
    }

    private void _generateContent(EmailData emailData, MimeMessageHelper helper) throws MessagingException {
        String content = transformator.transform(emailData, LOGO);
        helper.setText(content, transformator.isHtml());
        helper.addInline(LOGO.getName(), LOGO);

        File log = zip(FileUtil.getFileFromFileSystem(E2eConstant.E2E_LOG_FILE));
        helper.addAttachment(log.getName(), log);

        File webDriverLog = zip(FileUtil.getFileFromFileSystem(E2eConstant.WEB_DRIVER_LOG_FILE));
        helper.addAttachment(webDriverLog.getName(), webDriverLog);

        for (File attachment: emailData.getAttachments()) {
            if(attachment.exists()) {
                helper.addAttachment(attachment.getName(), attachment);
            }
        }
    }

}
