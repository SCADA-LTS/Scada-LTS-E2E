package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runners.Parameterized;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

@Log4j2
public class E2eTestParameterizedRunner extends Parameterized {

    public E2eTestParameterizedRunner(Class<?> clazz) throws Throwable {
        super(clazz);
        if(!E2eAbstractRunnable.isLogged()) {
            E2eAbstractRunnable.setup();
        }
    }

}
