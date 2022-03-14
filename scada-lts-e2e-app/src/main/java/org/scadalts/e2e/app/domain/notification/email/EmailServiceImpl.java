package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;

@Log4j2
@Service
class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final MimeMessageCreator mimeMessageCreator;

    public EmailServiceImpl(JavaMailSender javaMailSender, MimeMessageCreator mimeMessageCreator) {
        this.javaMailSender = javaMailSender;
        this.mimeMessageCreator = mimeMessageCreator;
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SentEmailsCacheConfig.SENT_EMAILS,
            key=SentEmailsCacheConfig.EMAIL_CACHE_KEY, unless = "#result == false")
    public boolean sendEmailFail(EmailData emailData) {
        return _send(emailData);
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SentEmailsCacheConfig.SENT_EMAILS_SUCCESS,
            key=SentEmailsCacheConfig.EMAIL_SUCCESS_CACHE_KEY, unless = "#result == false")
    public boolean sendEmailSuccess(EmailData emailData) {
        return _send(emailData);
    }

    private boolean _send(EmailData emailData) {
        boolean send = _sendEmail(emailData);
        if(!send) {
            logger.info("trying to send message again to... {}", emailData.getSendTo());
            return _sendEmail(emailData);
        }
        return send;
    }

    private boolean _sendEmail(EmailData emailData) {
        try {
            MimeMessage mail = mimeMessageCreator.create(emailData, javaMailSender);
            logger.info("sending email to... {}", emailData.getSendTo());
            javaMailSender.send(mail);
            logger.info("successful send");
            return true;
        } catch (Exception ex) {
            logger.warn(MessageFormat.format("failed send: {0}", ex.getMessage()), ex);
            return false;
        }
    }
}
