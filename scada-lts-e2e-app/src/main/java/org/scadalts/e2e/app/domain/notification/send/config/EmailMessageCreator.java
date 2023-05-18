package org.scadalts.e2e.app.domain.notification.send.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.scadalts.e2e.common.core.config.E2eConstant;
import org.scadalts.e2e.common.core.utils.FileUtil;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Optional;

import static org.scadalts.e2e.common.core.utils.FileUtil.zip;

@Log4j2
public class EmailMessageCreator extends SmsMessageCreator implements MimeMessageCreator {

    private static Optional<File> LOGO = FileUtil.getFileFromJar("templates/logo.png");

    public EmailMessageCreator(MessageTransformator emailMessageTransformator) {
        super(emailMessageTransformator);
    }

    @Override
    protected MimeMessageHelper createHelper(MimeMessage mail) throws MessagingException {
        return new MimeMessageHelper(mail, true, "UTF-8");
    }

    @Override
    protected void generateContent(MsgData msgData, MimeMessageHelper helper, MessageTransformator transformator) {

        String content = transformator.transform(msgData);
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

        for (File attachment: msgData.getAttachments()) {
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
