package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.FormatUtil;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChangePointValuesProvider {

    public static List<String> paramsToTests() {
        return _paramsToTests().stream()
                .map(FormatUtil::unformat)
                .collect(Collectors.toList());
    }

    private static List<String> _paramsToTests() {
        String[] values = TestImplConfiguration.dataPointValuesToChangeTests;
        if(!Objects.isNull(values) && values.length > 0)
            return Arrays.asList(values);
        return Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5");
    }
}
