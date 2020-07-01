package org.scadalts.e2e.test.impl.utils;

import lombok.Builder;
import lombok.Getter;
import org.scadalts.e2e.common.utils.VariationUnit;
import org.scadalts.e2e.page.impl.criterias.IdentifierObjectFactory;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointNotifierType;
import org.scadalts.e2e.service.impl.services.storungs.StorungAlarmResponse;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class TestDataBatch {

    private final VariationUnit<Integer> variationUnit;
    private final @Getter DataPointIdentifier dataPointIdentifier;
    private final @Getter int numberAlarmsWithStart;
    private final @Getter DataPointNotifierType dataPointNotifierType;

    @Builder
    public TestDataBatch(VariationUnit<Integer> variationUnit, DataPointNotifierType dataPointNotifierType) {
        this.variationUnit = variationUnit;
        this.dataPointIdentifier = IdentifierObjectFactory.dataPointNotifierBinaryTypeName(dataPointNotifierType);
        this.numberAlarmsWithStart = dataPointNotifierType == DataPointNotifierType.NONE ? 0 : StorungsAndAlarmsUtil.calculateRisingSlopes(variationUnit.getVariationWithStart(),
                0); //+ variationUnit.getStartValue() == 0 ? 1 : 0;
        this.dataPointNotifierType = dataPointNotifierType;
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

    public int getNumberAlarms() {
        return numberAlarmsWithStart; //- variationUnit.getStartValue() == 0 ? 1 : 0;
    }

    public int getNumberInactiveAlarms() {
        return getNumberAlarms() - getNumberActiveAlarms();
    }

    public int getNumberActiveAlarms() {
        return _isActivate() && dataPointNotifierType != DataPointNotifierType.NONE ? 1 : 0;
    }

    public int getNumberStartAlarms() {
        return variationUnit.getStartValue() == 1 && dataPointNotifierType != DataPointNotifierType.NONE ? 1 : 0;
    }

    private boolean _isActivate() {
        List<Integer> variation = variationUnit.getVariationWithStart();
        return variation.get(variation.size() - 1) == 1;
    }

    @Override
    public String toString() {
        return MessageFormat.format("variations: {0}, data point name: {1}, number alarms live[x]: {2}",
                variationUnit.getVariationWithStart(), dataPointIdentifier.getValue(), getNumberAlarmsWithStart());
    }
}
