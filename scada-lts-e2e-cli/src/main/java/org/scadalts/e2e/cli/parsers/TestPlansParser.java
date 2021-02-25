package org.scadalts.e2e.cli.parsers;

import org.scadalts.e2e.common.core.types.TestPlan;
import picocli.CommandLine.ITypeConverter;

public class TestPlansParser implements ITypeConverter<TestPlan> {

    @Override
    public TestPlan convert(String testPlans) throws Exception {
        return TestPlan.valueOf(testPlans.toUpperCase());
    }
}
