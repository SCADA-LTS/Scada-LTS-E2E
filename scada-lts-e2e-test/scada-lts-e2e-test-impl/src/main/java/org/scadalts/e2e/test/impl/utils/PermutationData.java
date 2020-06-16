package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@EqualsAndHashCode
public class PermutationData<T> {

    private final List<T> permutations;

    public PermutationData(List<T> permutations) {
        this.permutations = new ArrayList<>(permutations);
    }
}
