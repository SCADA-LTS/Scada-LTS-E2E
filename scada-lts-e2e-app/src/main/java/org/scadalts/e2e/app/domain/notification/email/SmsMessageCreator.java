package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Log4j2
@Component("smsMessageCreator")
public class SmsMessageCreator implements MimeMessageCreator {

    private final MessageTransformator transformator;

    SmsMessageCreator(@Qualifier("smsMessageTransformator")MessageTransformator smsMessageTransformator) {
        this.transformator = smsMessageTransformator;
    }

    @Override
    public MimeMessage create(EmailData emailData, JavaMailSender javaMailSender) throws MessagingException {
        logger.info("creating sms...");
        MimeMessage mail = javaMailSender.createMimeMessage();
        _createMime(emailData, mail);
        return mail;
    }

    private void _createMime(EmailData emailData, MimeMessage mail) throws MessagingException {
        MimeMessageHelper helper = new MimeMessageHelper(mail, false, "UTF-8");
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
    }
}
