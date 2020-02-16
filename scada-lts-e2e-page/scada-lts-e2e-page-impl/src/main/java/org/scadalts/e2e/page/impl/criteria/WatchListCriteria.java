package org.scadalts.e2e.page.impl.criteria;

import org.scadalts.e2e.common.dicts.E2eDictionary;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;

public class WatchListCriteria implements ObjectCriteria {

    private final String dataSourceXid;
    private final String dataPointXid;

    public WatchListCriteria(DataSourceCriteria dataSourceXid, DataPointCriteria dataPointXid) {
        this.dataSourceXid = dataSourceXid.getIdentifier();
        this.dataPointXid = dataPointXid.getIdentifier();
    }

    @Override
    public String getIdentifier() {
        return dataSourceXid + " - " + dataPointXid;
    }

    @Override
    public E2eDictionary getType() {
        return EmptyType.ANY;
    }
}
