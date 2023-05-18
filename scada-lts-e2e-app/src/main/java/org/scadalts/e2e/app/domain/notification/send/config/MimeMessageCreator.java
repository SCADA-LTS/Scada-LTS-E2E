package org.scadalts.e2e.app.domain.notification.send.config;

import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public interface MimeMessageCreator {
    MimeMessage create(MsgData msgData, JavaMailSender javaMailSender) throws MessagingException;
}
