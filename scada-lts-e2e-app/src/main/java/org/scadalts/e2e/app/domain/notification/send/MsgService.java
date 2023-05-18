package org.scadalts.e2e.app.domain.notification.send;


import org.scadalts.e2e.app.domain.notification.send.config.MimeMessageCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;

public interface MsgService {
    boolean sendFail(MsgData msgData);
    boolean sendSuccess(MsgData msgData);

    static MsgService newService(JavaMailSender javaMailSender,
                                 @Qualifier("emailMessageCreator") MimeMessageCreator emailMessageCreator,
                                 @Qualifier("smsMessageCreator") MimeMessageCreator smsMessageCreator) {
        return new MsgServiceImpl(javaMailSender, emailMessageCreator, smsMessageCreator);
    }
}
