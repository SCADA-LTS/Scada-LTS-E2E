package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ChangePointValuesProvider {

    public static Collection<String> paramsToTests() {
        String[] values = TestImplConfiguration.dataPointValuesToChangeTests;
        if(!Objects.isNull(values) && values.length > 0)
            return Arrays.asList(values);
        return Arrays.asList( "1" , "2" , "3");
    }
}
