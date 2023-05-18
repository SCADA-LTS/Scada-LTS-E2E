package org.scadalts.e2e.app.domain.notification.send.config;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.scadalts.e2e.common.core.config.SendTo;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Log4j2
public class SmsMessageCreator implements MimeMessageCreator {

    private final MessageTransformator transformator;

    public SmsMessageCreator(MessageTransformator smsMessageTransformator) {
        this.transformator = smsMessageTransformator;
    }

    @Override
    public MimeMessage create(MsgData msgData, JavaMailSender javaMailSender) throws MessagingException {
        logger.info("creating {}...", msgData.getSendTo().getDest() == SendTo.Destination.EMAIL ? "email" : "sms");
        MimeMessage mail = javaMailSender.createMimeMessage();
        _createMime(msgData, mail);
        return mail;
    }

    private void _createMime(MsgData msgData, MimeMessage mail) throws MessagingException {
        MimeMessageHelper helper = createHelper(mail);
        helper.setBcc(msgData.getSendTo().getAdress());
        helper.setFrom(msgData.getFrom());
        helper.setSubject(msgData.getTitle());

        generateContent(msgData, helper, transformator);
    }

    protected MimeMessageHelper createHelper(MimeMessage mail) throws MessagingException {
        return new MimeMessageHelper(mail, false, "UTF-8");
    }

    protected void generateContent(MsgData msgData, MimeMessageHelper helper, MessageTransformator transformator) {
        String content = transformator.transform(msgData);
        try {
            helper.setText(content, transformator.isHtml());
        } catch (MessagingException e) {
            logger.warn(e.getMessage(), e);
        }
    }
}
