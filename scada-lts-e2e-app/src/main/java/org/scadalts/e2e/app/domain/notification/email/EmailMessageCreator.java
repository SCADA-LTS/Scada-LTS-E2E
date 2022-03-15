package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.common.core.config.E2eConstant;
import org.scadalts.e2e.common.core.utils.FileUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Optional;

import static org.scadalts.e2e.common.core.utils.FileUtil.zip;

@Log4j2
@Component("emailMessageCreator")
class EmailMessageCreator implements MimeMessageCreator {

    private final MessageTransformator transformator;
    private static Optional<File> LOGO = FileUtil.getFileFromJar("templates/logo.png");

    EmailMessageCreator(@Qualifier("emailMessageTransformator")MessageTransformator emailMessageTransformator) {
        this.transformator = emailMessageTransformator;
    }

    @Override
    public MimeMessage create(EmailData emailData, JavaMailSender javaMailSender) throws MessagingException {
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

    private void _generateContent(EmailData emailData, MimeMessageHelper helper) {

        String content = transformator.transform(emailData);
        try {
            helper.setText(content, transformator.isHtml());
        } catch (MessagingException e) {
            logger.warn(e.getMessage(), e);
        }

        LOGO.ifPresent(a -> {
            try {
                helper.addInline(a.getName(), a);
            } catch (MessagingException e) {
                logger.warn(e.getMessage(), e);
            }
        });

        FileUtil.getFileFromFileSystem(E2eConstant.E2E_LOG_FILE).ifPresent(a -> {
            File zip = zip(a);
            try {
                helper.addAttachment(zip.getName(), zip);
            } catch (MessagingException e) {
                logger.warn(e.getMessage(), e);
            }
        });

        FileUtil.getFileFromFileSystem(E2eConstant.WEB_DRIVER_LOG_FILE).ifPresent(a -> {
            File webDriverLog = zip(a);
            try {
                helper.addAttachment(webDriverLog.getName(), webDriverLog);
            } catch (MessagingException e) {
                logger.warn(e.getMessage(), e);
            }
        });

        for (File attachment: emailData.getAttachments()) {
            if(attachment.exists()) {
                try {
                    helper.addAttachment(attachment.getName(), attachment);
                } catch (MessagingException e) {

                    logger.warn(e.getMessage(), e);
                }
            }
        }
    }
}
