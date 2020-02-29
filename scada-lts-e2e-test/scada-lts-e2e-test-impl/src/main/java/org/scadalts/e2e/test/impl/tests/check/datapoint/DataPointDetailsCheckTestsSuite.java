package org.scadalts.e2e.test.impl.tests.check.datapoint;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ChangePointValueInDetailsCheckTest.class,
        SequencePointValueHistoryInDetailsCheckTest.class
})
public class DataPointDetailsCheckTestsSuite {
}
