package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DataSourcePointIdentifier extends AbstractIdentifier {

    private final @NonNull DataSourceIdentifier dataSourceIdentifier;
    private final @NonNull DataPointIdentifier dataPointIdentifier;

    public final static DataSourcePointIdentifier EMPTY = new DataSourcePointIdentifier(DataSourceIdentifier.empty(),
            DataPointIdentifier.empty());

    public DataSourcePointIdentifier(@NonNull DataSourceIdentifier dataSourceIdentifier,
                                     @NonNull DataPointIdentifier dataPointIdentifier) {
        super(dataSourceIdentifier.getValue() + " - " + dataPointIdentifier.getValue());
        this.dataSourceIdentifier = dataSourceIdentifier;
        this.dataPointIdentifier = dataPointIdentifier;
    }

    public static DataSourcePointIdentifier empty() {
        return EMPTY;
    }
}