package org.scadalts.e2e.page.impl.dicts;

import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.impl.criterias.DataPointCriteria;

import java.util.stream.Stream;

public enum DataPointChangeFieldType implements DictionaryObject {

    BINARY_ALTERNATE_START_VALUE(DataPointType.BINARY, ChangeType.ALTERNATE, ChangeTypeField.START_VALUE),
    BINARY_NO_CHANGE_START_VALUE(EmptyType.ANY, ChangeType.NO_CHANGE, ChangeTypeField.START_VALUE),
    BINARY_RANDOM_START_VALUE(DataPointType.BINARY, ChangeType.RANDOM, ChangeTypeField.START_VALUE),

    MULTISTATE_INCREMENT_VALUES(DataPointType.MULTISTATE, ChangeType.INCREMENT, EmptyType.ANY),
    MULTISTATE_INCREMENT_ROLL(DataPointType.MULTISTATE, ChangeType.INCREMENT, ChangeTypeField.ROLL),
    MULTISTATE_INCREMENT_START_VALUE(DataPointType.MULTISTATE, ChangeType.INCREMENT, ChangeTypeField.START_VALUE),

    MULTISTATE_RANDOM_START_VALUE(DataPointType.MULTISTATE, ChangeType.RANDOM, ChangeTypeField.START_VALUE),
    MULTISTATE_RANDOM_VALUES(DataPointType.MULTISTATE, ChangeType.RANDOM, EmptyType.ANY),

    NUMERIC_BROWNIAN_MIN(DataPointType.NUMERIC, ChangeType.BROWNIAN, ChangeTypeField.MIN),
    NUMERIC_BROWNIAN_MAX(DataPointType.NUMERIC, ChangeType.BROWNIAN, ChangeTypeField.MAX),
    NUMERIC_BROWNIAN_MAX_CHANGE(DataPointType.NUMERIC, ChangeType.BROWNIAN, ChangeTypeField.MAX_CHANGE),
    NUMERIC_INCREMENT_MIN(DataPointType.NUMERIC, ChangeType.INCREMENT, ChangeTypeField.MIN),
    NUMERIC_INCREMENT_MAX(DataPointType.NUMERIC, ChangeType.INCREMENT, ChangeTypeField.MAX),
    NUMERIC_INCREMENT_CHANGE(DataPointType.NUMERIC, ChangeType.INCREMENT, ChangeTypeField.CHANGE),
    NUMERIC_INCREMENT_ROLL(DataPointType.NUMERIC, ChangeType.INCREMENT, ChangeTypeField.ROLL),
    NUMERIC_INCREMENT_START_VALUE(DataPointType.NUMERIC, ChangeType.INCREMENT, ChangeTypeField.START_VALUE),

    NUMERIC_RANDOM_MIN(DataPointType.NUMERIC, ChangeType.RANDOM, ChangeTypeField.MIN),
    NUMERIC_RANDOM_MAX(DataPointType.NUMERIC, ChangeType.RANDOM, ChangeTypeField.MAX),
    NUMERIC_RANDOM_START_VALUE(DataPointType.NUMERIC, ChangeType.RANDOM, ChangeTypeField.START_VALUE),

    NUMERIC_ATTRACTOR_MAX_CHANGE(DataPointType.NUMERIC, ChangeType.ATTRACTOR, ChangeTypeField.MAX_CHANGE),
    NUMERIC_ATTRACTOR_VOLATILITY(DataPointType.NUMERIC, ChangeType.ATTRACTOR, ChangeTypeField.VOLATILITY),
    NUMERIC_ATTRACTOR_ATTRACTION_POINT(DataPointType.NUMERIC, ChangeType.ATTRACTOR, ChangeTypeField.ATTRACTION_POINT),
    NUMERIC_ATTRACTOR_START_VALUE(DataPointType.NUMERIC, ChangeType.ATTRACTOR, ChangeTypeField.START_VALUE),

    NO_CHANGE_START_VALUE(EmptyType.ANY, ChangeType.NO_CHANGE, ChangeTypeField.START_VALUE);

    private final DictionaryObject dataPointType;
    private final ChangeType changeType;
    private final DictionaryObject changeTypeField;

    DataPointChangeFieldType(DictionaryObject dataPointType, ChangeType changeType, DictionaryObject changeTypeField) {
        this.dataPointType = dataPointType;
        this.changeType = changeType;
        this.changeTypeField = changeTypeField;
    }

    @Override
    public String getName() {
        return this.getName() + dataPointType.getName() + changeTypeField.getName();
    }

    @Override
    public String getId() {
        return _getPrefix() + _getSuffix();
    }

    public static DataPointChangeFieldType getType(DataPointCriteria criteria, ChangeTypeField changeTypeField) {
        return Stream.of(DataPointChangeFieldType.values())
                .filter(a -> a.dataPointType == criteria.getType())
                .filter(a -> a.changeType == criteria.getChangeType())
                .filter(a -> a.changeTypeField == changeTypeField)
                .findFirst()
                .orElse(NO_CHANGE_START_VALUE);
    }

    private String _getPrefix() {
        return changeType.getId() + dataPointType.getId() + (_isNotValues() && this != NO_CHANGE_START_VALUE ? "Change" : "");
    }

    private boolean _isNotValues() {
        return this != MULTISTATE_RANDOM_VALUES
                && this != MULTISTATE_INCREMENT_VALUES;
    }

    private String _getSuffix() {
        return (_isNotValues() ? "." + changeTypeField.getId() : "");
    }
}
