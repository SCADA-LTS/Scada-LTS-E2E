package org.scadalts.e2e.app.domain.notification.email;

import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
    public boolean sendEmail(EmailData emailData) {
        try {
            MimeMessage mail = mimeMessageCreator.create(emailData, javaMailSender);
            javaMailSender.send(mail);
            return true;
        } catch (MailException | MessagingException e) {
            logger.warn(e.getMessage(), e);
            return false;
        }
    }

}
