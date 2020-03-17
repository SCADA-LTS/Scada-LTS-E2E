package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.FormatUtil;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChangePointValuesProvider {

    public static Collection<String> paramsToTests() {
        return _paramsToTests().stream()
                .map(FormatUtil::unformat)
                .collect(Collectors.toList());
    }

    private static Collection<String> _paramsToTests() {
        String[] values = TestImplConfiguration.dataPointValuesToChangeTests;
        if(!Objects.isNull(values) && values.length > 0)
            return Arrays.asList(values);
        return Arrays.asList("1", "2", "3", "4", "5");
    }
}
