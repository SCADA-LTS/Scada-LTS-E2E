package org.scadalts.e2e.common.utils;

import java.util.ArrayList;
import java.util.List;

public class VariationsGenerator {

    public static List<VariationUnit<Integer>> generate(int toValue, int nWords, int startValue) {
        List<VariationUnit<Integer>> result = new ArrayList<>();
        _generate(toValue, nWords, new ArrayList<>(), result, startValue);
        return result;
    }

    private static void _generate(int toValue, int nWords, List<Integer> variation, List<VariationUnit<Integer>> result,
                                  int startValue) {

        if(nWords == 0) {
            result.add(VariationUnit.<Integer>builder()
                            .startValue(startValue)
                            .variation(variation)
                            .build());
            return;
        }

        for(int i = 0; i < toValue + 1; i++) {
            variation.add(0, i);
            _generate(toValue, nWords-1, variation, result, startValue);
            variation.remove(0);
        }
    }
}
