package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;

import java.text.MessageFormat;

@Getter
public class PermutationTestData {

    private final PermutationData<Integer> permutationData;
    private final DataPointIdentifier dataPointIdentifier;
    private final int numberRisingSlopes;

    public PermutationTestData(PermutationData<Integer> permutationData, DataPointIdentifier dataPointIdentifier) {
        this.permutationData = permutationData;
        this.dataPointIdentifier = dataPointIdentifier;
        this.numberRisingSlopes = AlarmsAndStorungsUtil.calculateRisingSlopes(permutationData.getPermutations());
    }

    @Override
    public String toString() {
        return MessageFormat.format("permutations: {0}, data point name: {1}, live: {2}",
                permutationData.getPermutations(), dataPointIdentifier.getValue(), numberRisingSlopes);
    }
}
