package org.scadalts.e2e.app.domain.notification.reaction;

import org.scadalts.e2e.app.domain.notification.send.MsgData;
import org.scadalts.e2e.app.domain.notification.send.MsgService;
import org.scadalts.e2e.app.domain.notification.send.config.EmailMessageCreator;
import org.scadalts.e2e.app.domain.notification.send.config.MimeMessageCreator;
import org.scadalts.e2e.app.domain.notification.send.config.SmsMessageCreator;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.config.SendTo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class E2eConfigTestConfig {

    static JavaMailSender javaMailSender;

    @Bean
    static JavaMailSender javaMailSender(MimeMessage mimeMessage) {
        if(javaMailSender == null) {
            javaMailSender = mock(JavaMailSender.class);
            when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        }
        return javaMailSender;
    }

    static EmailMessageCreator emailMessageCreator;

    @Bean
    static EmailMessageCreator emailMessageCreator(JavaMailSender javaMailSender, MimeMessage mimeMessage) throws MessagingException {
        if(emailMessageCreator == null) {
            emailMessageCreator = mock(EmailMessageCreator.class);
            when(emailMessageCreator.create(any(MsgData.class),
                    eq(javaMailSender))).thenReturn(mimeMessage);
        }
        return emailMessageCreator;
    }

    static SmsMessageCreator smsMessageCreator;

    @Bean
    static SmsMessageCreator smsMessageCreator(JavaMailSender javaMailSender, MimeMessage mimeMessage) throws MessagingException {
        if(smsMessageCreator == null) {
            smsMessageCreator = mock(SmsMessageCreator.class);
            when(smsMessageCreator.create(any(MsgData.class),
                    eq(javaMailSender))).thenReturn(mimeMessage);
        }
        return smsMessageCreator;
    }

    static MimeMessage mimeMessage;

    @Bean
    static MimeMessage mimeMessage() {
        if(mimeMessage == null) {
            mimeMessage = mock(MimeMessage.class);
        }
        return mimeMessage;
    }

    @Bean("oneSendEmailConfig")
    E2eConfig oneSendEmailConfig() {
        Set<SendTo> sends = new HashSet<>();
        sends.add(SendTo.sendTo("abc@abc.com", SendTo.Destination.EMAIL));
        return E2eConfig.config(sends, "Email Test Fail","Email Test Success", "junit", "hostSmtp");
    }

    @Bean("twoSendEmailConfig")
    E2eConfig twoSendEmailConfig() {
        Set<SendTo> sends = new HashSet<>();
        sends.add(SendTo.sendTo("abc@abc.com", SendTo.Destination.EMAIL));
        sends.add(SendTo.sendTo("abc2@abc.com", SendTo.Destination.EMAIL));
        return E2eConfig.config(sends, "Email Test Fail","Email Test Success", "junit", "hostSmtp");
    }

    @Bean
    E2eConfig e2eConfig() {
        Set<SendTo> sends = new HashSet<>();
        sends.add(SendTo.sendTo("abc@abc.com", SendTo.Destination.EMAIL));
        sends.add(SendTo.sendTo("abc2@abc.com", SendTo.Destination.EMAIL));
        return E2eConfig.config(sends, "Email Test Fail","Email Test Success", "junit", "hostSmtp");
    }

    @Bean
    MsgService msgService(JavaMailSender javaMailSender,
                          @Qualifier("emailMessageCreator") MimeMessageCreator emailMessageCreator,
                          @Qualifier("smsMessageCreator") MimeMessageCreator smsMessageCreator) {
        return MsgService.newService(javaMailSender, emailMessageCreator, smsMessageCreator);
    }

}
