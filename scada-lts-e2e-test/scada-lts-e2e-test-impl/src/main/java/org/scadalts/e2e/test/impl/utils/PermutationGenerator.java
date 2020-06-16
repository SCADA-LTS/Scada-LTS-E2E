package org.scadalts.e2e.test.impl.utils;

import java.util.ArrayList;
import java.util.List;

public class PermutationGenerator {

    public static List<PermutationData<Integer>> generate(int toValue, int nWords) {
        List<PermutationData<Integer>> result = new ArrayList<>();
        _generate(toValue, nWords, new ArrayList<>(), result);
        return result;
    }

    private static void _generate(int toValue, int nWords, List<Integer> cur, List<PermutationData<Integer>> result) {

        if(nWords == 0) {
            result.add(new PermutationData(cur));
            return;
        }

        for(int i = 0; i < toValue + 1; i++) {
            cur.add(0, i);
            _generate(toValue, nWords-1, cur, result);
            cur.remove(0);
        }
    }
}
