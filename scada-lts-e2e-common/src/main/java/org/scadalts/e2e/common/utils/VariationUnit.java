package org.scadalts.e2e.common.utils;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Builder
@EqualsAndHashCode
public class VariationUnit<T> {

    private final @Singular("sequence") List<T> variation;
    private final T startValue;
    private final T endValue;

    private VariationUnit(List<T> variation, T startValue, T endValue) {
        this.variation = new ArrayList<>(variation);
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public List<T> getVariationWithStart() {
        List<T> result = new ArrayList<>();
        if(startValue != null)
            result.add(startValue);
        result.addAll(variation);
        if(endValue != null)
            result.add(endValue);
        return result;
    }


    public List<T> getVariation() {
        List<T> result = new ArrayList<>();
        result.addAll(variation);
        if(endValue != null)
            result.add(endValue);
        return result;
    }

    public T getStartValue() {
        if(startValue != null) {
            return startValue;
        }
        if(!variation.isEmpty()) {
            return variation.get(0);
        }
        return null;
    }

    public T getEndValue() {
        if(endValue != null) {
            return endValue;
        }
        if(!variation.isEmpty()) {
            return variation.get(variation.size() - 1);
        }
        return null;
    }
}
