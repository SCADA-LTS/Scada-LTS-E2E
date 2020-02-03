package org.scadalts.e2e.app.parsers;

import org.scadalts.e2e.common.types.TestPlan;
import picocli.CommandLine.ITypeConverter;

public class TestPlansParser implements ITypeConverter<TestPlan> {

    @Override
    public TestPlan convert(String testPlans) throws Exception {
        return TestPlan.valueOf(testPlans.toUpperCase());
    }
}
