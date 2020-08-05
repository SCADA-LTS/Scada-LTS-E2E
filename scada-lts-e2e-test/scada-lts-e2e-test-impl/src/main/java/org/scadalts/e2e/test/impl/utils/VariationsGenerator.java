package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.common.utils.FileUtil;
import org.scadalts.e2e.common.utils.VariationUnit;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VariationsGenerator {

    public static List<VariationUnit<Integer>> generate(int toValue, int nWords, int startValue) {
        List<VariationUnit<Integer>> result = new ArrayList<>();
        _generate(toValue, nWords, new ArrayList<>(), result, startValue, null);
        return result;
    }

    public static List<VariationUnit<Integer>> generateEndValue(int toValue, int nWords, int endValue) {
        List<VariationUnit<Integer>> result = new ArrayList<>();
        _generate(toValue, nWords, new ArrayList<>(), result, null, endValue);
        return result;
    }

    public static List<VariationUnit<Integer>> generateRandom(int toValue, int nWords, int size) {
        List<VariationUnit<Integer>> result = new ArrayList<>();
        Random random = new Random();
        int bound = toValue + 1;

        for (int i = 0 ; i < size ; i++) {
            List<Integer> variatons = new ArrayList<>();
            for (int j = 0; j < nWords; j++) {
                variatons.add(random.nextInt(bound));
            }
            result.add(VariationUnit.<Integer>builder()
                    .startValue(random.nextInt(bound))
                    .variation(variatons)
                    .build());
        }
        return result;
    }

    public static VariationUnit<Integer> generateFromFile(Path path) {

        List<String> list = FileUtil.readLines(path);
        List<Integer> variations = new ArrayList<>();
        for (String var : list) {
            variations.add(Integer.valueOf(var));
        }
        return VariationUnit.<Integer>builder()
                .variation(variations)
                .build();
    }

    public static List<VariationUnit<Integer>> generateZeroToOnes(int nWords, int size) {
        List<VariationUnit<Integer>> result = new ArrayList<>();
        for (int i = 0 ; i < size ; i++) {
            List<Integer> variatons = new ArrayList<>();
            for (int j = 0; j < nWords; j++) {
                variatons.add(1);
            }
            result.add(VariationUnit.<Integer>builder()
                    .startValue(0)
                    .variation(variatons)
                    .build());
        }
        return result;
    }

    private static void _generate(int toValue, int nWords, List<Integer> variation, List<VariationUnit<Integer>> result,
                                  Integer startValue, Integer endValue) {

        if(nWords == 0) {
            result.add(VariationUnit.<Integer>builder()
                            .startValue(startValue)
                            .variation(variation)
                            .endValue(endValue)
                            .build());
            return;
        }

        for(int i = 0; i < toValue + 1; i++) {
            variation.add(0, i);
            _generate(toValue, nWords-1, variation, result, startValue, endValue);
            variation.remove(0);
        }
    }
}
