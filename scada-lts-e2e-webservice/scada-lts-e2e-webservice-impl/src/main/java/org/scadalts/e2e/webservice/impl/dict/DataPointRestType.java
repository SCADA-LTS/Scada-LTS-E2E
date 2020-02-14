package org.scadalts.e2e.webservice.impl.dict;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import org.scadalts.e2e.common.dicts.E2eDictionary;

import java.util.stream.Stream;

@Getter
public enum DataPointRestType implements E2eDictionary {

    BINARY_VALUE("BinaryValue"),
    NUMERIC_VALUE("NumericValue");

    private String typeName;

    DataPointRestType(String typeName) {
        this.typeName = typeName;
    }

    @JsonCreator
    public static DataPointRestType fromString(String string) {
        return Stream.of(DataPointRestType.values())
                .filter(a -> a.typeName.equals(string))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(string));
    }
}
