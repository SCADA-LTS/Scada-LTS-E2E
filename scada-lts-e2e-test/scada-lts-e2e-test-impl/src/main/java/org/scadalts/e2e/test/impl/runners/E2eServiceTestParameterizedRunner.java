package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.common.config.E2eConfiguration;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.service.impl.services.login.LoginParams;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;
import org.scadalts.e2e.test.impl.utils.ServiceTestsUtil;

import static org.scadalts.e2e.test.impl.utils.ServiceTestsUtil.login;

@Log4j2
public class E2eServiceTestParameterizedRunner extends Parameterized {

    public E2eServiceTestParameterizedRunner(Class<?> clazz) throws Throwable {
        super(clazz);
    }

    @Override
    public void run(RunNotifier notifier) {
        try {
            if (!ServiceTestsUtil.isLogged()) {
                E2eAbstractRunnable.setup();
                LoginParams loginParams = LoginParams.builder()
                        .userName(E2eConfiguration.userName)
                        .password(E2eConfiguration.password)
                        .build();
                E2eConfiguration.sessionId = login(loginParams).getSessionId();
            }
        } catch (Throwable throwable) {
            NavigationPage.kill();
            throw throwable;
        }
        super.run(notifier);
    }
}