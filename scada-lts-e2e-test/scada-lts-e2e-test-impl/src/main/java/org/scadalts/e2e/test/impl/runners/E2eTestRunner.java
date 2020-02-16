package org.scadalts.e2e.test.impl.runners;

import lombok.extern.log4j.Log4j2;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.scadalts.e2e.test.impl.tests.E2eAbstractRunnable;

@Log4j2
public class E2eTestRunner extends BlockJUnit4ClassRunner {

    public E2eTestRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
        if(!E2eAbstractRunnable.isLogged()) {
            E2eAbstractRunnable.setup();
        }
    }
}