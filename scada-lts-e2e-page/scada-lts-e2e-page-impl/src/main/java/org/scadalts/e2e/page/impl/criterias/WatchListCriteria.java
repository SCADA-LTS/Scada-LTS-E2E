package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.criterias.identifiers.WatchListIdentifier;

import java.util.Arrays;
import java.util.List;

@Data
public class WatchListCriteria implements CriteriaObject {

    private final WatchListIdentifier identifier;
    private final List<DataSourcePointIdentifier> dataSourcePointIdentifiers;

    private final static WatchListCriteria EMPTY = new WatchListCriteria(WatchListIdentifier.empty(),
            DataSourcePointIdentifier.empty());

    public static WatchListCriteria empty() {
        return EMPTY;
    }

    public WatchListCriteria(WatchListIdentifier identifier, DataSourcePointIdentifier... dataSourcePointIdentifiers) {
        this.identifier = identifier;
        this.dataSourcePointIdentifiers = Arrays.asList(dataSourcePointIdentifiers);
    }

    public static WatchListCriteria criteria(DataSourcePointIdentifier... identifier) {
        return new WatchListCriteria(IdentifierObjectFactory.watchListName(), identifier);
    }

    public static WatchListCriteria criteria(WatchListIdentifier identifier,
                                             DataSourcePointIdentifier... dataSourcePointIdentifiers) {
        return new WatchListCriteria(identifier, dataSourcePointIdentifiers);
    }
}
