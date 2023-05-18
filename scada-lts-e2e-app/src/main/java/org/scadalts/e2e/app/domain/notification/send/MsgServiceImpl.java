package org.scadalts.e2e.app.domain.notification.send;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.app.domain.notification.blocked.config.SendMsgsCacheConfig;
import org.scadalts.e2e.app.domain.notification.send.config.MimeMessageCreator;
import org.scadalts.e2e.app.infrastructure.metrics.Logging;
import org.scadalts.e2e.common.core.config.SendTo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.function.BiPredicate;

@Log4j2
@Service
class MsgServiceImpl implements MsgService {

    private final JavaMailSender javaMailSender;
    private final MimeMessageCreator emailMessageCreator;
    private final MimeMessageCreator smsMessageCreator;

    public MsgServiceImpl(JavaMailSender javaMailSender,
                          @Qualifier("emailMessageCreator") MimeMessageCreator emailMessageCreator,
                          @Qualifier("smsMessageCreator") MimeMessageCreator smsMessageCreator) {
        this.javaMailSender = javaMailSender;
        this.emailMessageCreator = emailMessageCreator;
        this.smsMessageCreator = smsMessageCreator;
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SendMsgsCacheConfig.SENT_MSG_FAIL,
            key= SendMsgsCacheConfig.MSG_CACHE_KEY, unless = "#result == false")
    public boolean sendFail(MsgData msgData) {
        SendTo sentTo = msgData.getSendTo();
        if(sentTo.getDest() == SendTo.Destination.SMS)
            return _send(new SendData(msgData, smsMessageCreator), javaMailSender, MsgServiceImpl::_sendMsg);
        return _send(new SendData(msgData, emailMessageCreator), javaMailSender, MsgServiceImpl::_sendMsg);
    }

    @Override
    @Logging
    @Cacheable(cacheNames = SendMsgsCacheConfig.SENT_MSG_SUCCESS,
            key= SendMsgsCacheConfig.MSG_SUCCESS_CACHE_KEY, unless = "#result == false")
    public boolean sendSuccess(MsgData msgData) {
        SendTo sentTo = msgData.getSendTo();
        if(sentTo.getDest() == SendTo.Destination.SMS)
            return _send(new SendData(msgData, smsMessageCreator), javaMailSender, MsgServiceImpl::_sendMsg);
        return _send(new SendData(msgData, emailMessageCreator), javaMailSender, MsgServiceImpl::_sendMsg);
    }

    private static boolean _send(SendData sendData, JavaMailSender javaMailSender, BiPredicate<JavaMailSender, SendData> fun) {
        boolean send = fun.test(javaMailSender, sendData);
        if(!send) {
            MsgData msgData = sendData.getMsgData();
            logger.info("trying to send message again to... {}", msgData.getSendTo());
            return fun.test(javaMailSender, sendData);
        }
        return true;
    }

    private static boolean _sendMsg(JavaMailSender javaMailSender, SendData sendData) {
        try {
            MsgData msgData = sendData.getMsgData();
            SendTo sendTo = msgData.getSendTo();
            MimeMessageCreator creator = sendData.getCreator();
            String whatIsShipped = sendTo.getDest() == SendTo.Destination.EMAIL ? "email" : "sms";
            MimeMessage mail = creator.create(msgData, javaMailSender);
            logger.info("sending {} to... {}", whatIsShipped, sendTo);
            javaMailSender.send(mail);
            logger.info("successful send");
            return true;
        } catch (Exception ex) {
            logger.warn(MessageFormat.format("failed send: {0}", ex.getMessage()), ex);
            return false;
        }
    }

    @Getter
    @AllArgsConstructor
    static class SendData {
        private MsgData msgData;
        private MimeMessageCreator creator;
    }
}
