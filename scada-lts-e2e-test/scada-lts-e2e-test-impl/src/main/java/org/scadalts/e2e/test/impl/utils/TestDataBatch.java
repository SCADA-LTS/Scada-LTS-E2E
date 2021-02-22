package org.scadalts.e2e.test.impl.utils;

import lombok.Builder;
import lombok.Getter;
import org.scadalts.e2e.common.core.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.page.impl.dicts.LoggingType;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TestDataBatch {

    private final VariationUnit<Integer> variationUnit;
    private final @Getter DataPointIdentifier dataPointIdentifier;
    private final @Getter int numberAlarmsWithStart;
    private final @Getter DataPointNotifierType dataPointNotifierType;
    private final @Getter LoggingType loggingType;

    @Builder
    public TestDataBatch(VariationUnit<Integer> variationUnit, DataPointNotifierType dataPointNotifierType, LoggingType loggingType) {
        this.variationUnit = variationUnit;
        this.dataPointIdentifier = IdentifierObjectFactory.dataPointNotifierBinaryTypeName(dataPointNotifierType);
        this.numberAlarmsWithStart = dataPointNotifierType == DataPointNotifierType.NONE ? 0 : StorungsAndAlarmsUtil.calculateRisingSlopes(variationUnit.getVariationWithStart(),
                0);
        this.dataPointNotifierType = dataPointNotifierType;
        this.loggingType = loggingType;
    }

    public int getStartValue() {
        return variationUnit.getStartValue() != null ? variationUnit.getStartValue() : -1;
    }

    public List<Integer> getSequencePointValue() {
        return new ArrayList<>(variationUnit.getVariation());
    }

    public List<Integer> getSequencePointValueWithStart() {
        return new ArrayList<>(variationUnit.getVariationWithStart());
    }

    public String getDateText(StorungAlarmResponse storungAlarmResponse) {
        return getStartValue() == 0 ? storungAlarmResponse.getInactivationTime() : storungAlarmResponse.getActivationTime();
    }

    public int getAlarmsNumber() {
        return numberAlarmsWithStart; //- variationUnit.getStartValue() == 0 ? 1 : 0;
    }

    public int getInactiveAlarmsNumber() {
        return getAlarmsNumber() - getActiveAlarmsNumber();
    }

    public int getActiveAlarmsNumber() {
        return _isActivate() && dataPointNotifierType != DataPointNotifierType.NONE ? 1 : 0;
    }

    public int getStartAlarmsNumber() {
        return variationUnit.getStartValue() == 1 && dataPointNotifierType != DataPointNotifierType.NONE ? 1 : 0;
    }

    private boolean _isActivate() {
        List<Integer> variation = variationUnit.getVariationWithStart();
        if(variation.isEmpty())
            return false;
        return variation.get(variation.size() - 1) == 1;
    }

    @Override
    public String toString() {
        return MessageFormat.format("variations: {0}, data point name: {1}, size lives[x]: {2}, logging type: {3}",
                variationUnit.getVariationWithStart(), dataPointIdentifier.getValue(), getNumberAlarmsWithStart(), loggingType);
    }
}
