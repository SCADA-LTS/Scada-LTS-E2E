package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.scadalts.e2e.common.core.config.SendTo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.function.Predicate;

@Log4j2
@Service
class EmailService implements MsgService {

    private final JavaMailSender javaMailSender;
    private final MimeMessageCreator emailMessageCreator;
    private final MimeMessageCreator smsMessageCreator;

    public EmailService(JavaMailSender javaMailSender,
                        @Qualifier("emailMessageCreator") MimeMessageCreator emailMessageCreator,
                        @Qualifier("smsMessageCreator") MimeMessageCreator smsMessageCreator) {
        this.javaMailSender = javaMailSender;
        this.emailMessageCreator = emailMessageCreator;
        this.smsMessageCreator = smsMessageCreator;
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SentEmailsCacheConfig.SENT_EMAILS,
            key=SentEmailsCacheConfig.EMAIL_CACHE_KEY, unless = "#result == false")
    public boolean sendFail(EmailData emailData) {
        SendTo sentTo = emailData.getSendTo();
        if(sentTo.getDest() == SendTo.Destination.SMS)
            return _send(emailData, this::_sendSms);
        return _send(emailData, this::_sendEmail);
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS,
            key=SentEmailsCacheConfig.EMAIL_SUCCESS_CACHE_KEY, unless = "#result == false")
    public boolean sendSuccess(EmailData emailData) {
        SendTo sentTo = emailData.getSendTo();
        if(sentTo.getDest() == SendTo.Destination.SMS)
            return _send(emailData, this::_sendSms);
        return _send(emailData, this::_sendEmail);
    }

    private boolean _send(EmailData emailData, Predicate<EmailData> fun) {
        boolean send = fun.test(emailData);
        if(!send) {
            logger.info("trying to send message again to... {}", emailData.getSendTo());
            return fun.test(emailData);
        }
        return send;
    }

    private boolean _sendEmail(EmailData emailData) {
        try {
            MimeMessage mail = emailMessageCreator.create(emailData, javaMailSender);
            logger.info("sending email to... {}", emailData.getSendTo());
            javaMailSender.send(mail);
            logger.info("successful send");
            return true;
        } catch (Exception ex) {
            logger.warn(MessageFormat.format("failed send: {0}", ex.getMessage()), ex);
            return false;
        }
    }

    private boolean _sendSms(EmailData emailData) {
        try {
            MimeMessage mail = smsMessageCreator.create(emailData, javaMailSender);
            logger.info("sending sms to... {}", emailData.getSendTo());
            javaMailSender.send(mail);
            logger.info("successful send");
            return true;
        } catch (Exception ex) {
            logger.warn(MessageFormat.format("failed send: {0}", ex.getMessage()), ex);
            return false;
        }
    }
}
