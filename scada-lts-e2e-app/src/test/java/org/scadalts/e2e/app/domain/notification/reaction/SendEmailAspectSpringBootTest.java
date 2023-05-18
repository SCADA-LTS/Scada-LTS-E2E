package org.scadalts.e2e.app.domain.notification.reaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.scadalts.e2e.app.domain.notification.blocked.config.MsgCacheCleaner;
import org.scadalts.e2e.app.domain.notification.blocked.config.SendMsgsCacheConfig;
import org.scadalts.e2e.app.domain.notification.send.MsgService;
import org.scadalts.e2e.common.core.config.E2eConfig;
import org.scadalts.e2e.common.core.config.SendTo;
import org.scadalts.e2e.test.core.plans.engine.E2eResult;
import org.scadalts.e2e.test.core.plans.engine.E2eSummarable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.mail.internet.MimeMessage;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { E2eConfigTestConfig.class,
        SendMsgsCacheConfig.class}, loader = AnnotationConfigContextLoader.class)
public class SendEmailAspectSpringBootTest {

    private SendMsgAspect sendEmailAspect;
    private Set<SendTo> sends;
    private JavaMailSender javaMailSenderMock;
    private E2eSummarable failSummary;
    private E2eSummarable successSummary;

    @Autowired
    private @Qualifier("emailCacheCleaner") MsgCacheCleaner emailCacheCleaner;

    @Autowired
    private @Qualifier("oneSendEmailConfig") E2eConfig oneSendEmailConfig;

    @Autowired
    private @Qualifier("twoSendEmailConfig") E2eConfig twoSendEmailConfig;

    @Autowired
    private MsgService msgService;

    @Before
    public void config() {
        javaMailSenderMock = E2eConfigTestConfig.javaMailSender(null);
        sendEmailAspect = new SendMsgAspect(emailCacheCleaner, msgService, twoSendEmailConfig);

        Result failResultMock = mock(Result.class);
        when(failResultMock.wasSuccessful()).thenReturn(false);
        Map<Class<?>, List<E2eResult>> failResults = new HashMap<>();
        failResults.put(Object.class, Arrays.asList(E2eResult.builder().result(failResultMock).testName("Test1").build()));
        failSummary = E2eSummarable.summary(failResults);

        Result succesResultMock = mock(Result.class);
        when(succesResultMock.wasSuccessful()).thenReturn(true);
        Map<Class<?>, List<E2eResult>> successResults = new HashMap<>();
        successResults.put(Object.class, Arrays.asList(E2eResult.builder().result(succesResultMock).testName("Test1").build()));
        successSummary = E2eSummarable.summary(successResults);

        sends = twoSendEmailConfig.getSendTo();
    }

    @After
    public void clean() {
        reset(javaMailSenderMock);
        emailCacheCleaner.removeMsgFailAll();
        emailCacheCleaner.removeMsgSuccessAll();
    }

    @Test
    public void when_reaction_with_two_sendTo_and_two_successes_then_two_send() {
        //when:
        sendEmailAspect.reaction(successSummary);
        sendEmailAspect.reaction(successSummary);

        //then:
        verify(javaMailSenderMock, times(sends.size())).send(any(MimeMessage.class));
    }

    @Test
    public void when_reaction_with_two_sendTo_and_two_fails_then_two_send() {
        //when:
        sendEmailAspect.reaction(failSummary);
        sendEmailAspect.reaction(failSummary);

        //then:
        verify(javaMailSenderMock, times(sends.size())).send(any(MimeMessage.class));
    }

    @Test
    public void when_reaction_with_two_sendTo_and_success_and_two_fails_then_four_send() {
        //given:
        sendEmailAspect.reaction(successSummary);

        //when:
        sendEmailAspect.reaction(failSummary);
        sendEmailAspect.reaction(failSummary);

        //then:
        verify(javaMailSenderMock, times(2*sends.size())).send(any(MimeMessage.class));
    }

    @Test
    public void when_reaction_with_one_sendTo_and_two_successes_then_one_send() {
        //given:
        sendEmailAspect = new SendMsgAspect(emailCacheCleaner, msgService, oneSendEmailConfig);
        sends = oneSendEmailConfig.getSendTo();

        //when:
        sendEmailAspect.reaction(successSummary);
        sendEmailAspect.reaction(successSummary);

        //then:
        verify(javaMailSenderMock, times(sends.size())).send(any(MimeMessage.class));
    }

    @Test
    public void when_reaction_with_one_sendTo_and_two_fails_then_one_send() {
        //given:
        sendEmailAspect = new SendMsgAspect(emailCacheCleaner, msgService, oneSendEmailConfig);
        sends = oneSendEmailConfig.getSendTo();

        //when:
        sendEmailAspect.reaction(failSummary);
        sendEmailAspect.reaction(failSummary);

        //then:
        verify(javaMailSenderMock, times(sends.size())).send(any(MimeMessage.class));
    }

    @Test
    public void when_reaction_with_one_sendTo_and_success_and_two_fails_then_two_send() {
        //given:
        sendEmailAspect = new SendMsgAspect(emailCacheCleaner, msgService, oneSendEmailConfig);
        sendEmailAspect.reaction(successSummary);
        sends = oneSendEmailConfig.getSendTo();

        //when:
        sendEmailAspect.reaction(failSummary);
        sendEmailAspect.reaction(failSummary);

        //then:
        verify(javaMailSenderMock, times(2*sends.size())).send(any(MimeMessage.class));
    }
}