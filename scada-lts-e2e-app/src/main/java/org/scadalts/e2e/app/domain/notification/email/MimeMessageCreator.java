package org.scadalts.e2e.app.domain.notification.email;

import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MimeMessageCreator {
    MimeMessage create(EmailData emailData, JavaMailSender javaMailSender) throws MessagingException;
}
