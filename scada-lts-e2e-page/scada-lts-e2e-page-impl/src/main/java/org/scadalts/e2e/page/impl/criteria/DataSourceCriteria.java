package org.scadalts.e2e.page.impl.criteria;

import lombok.Data;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.impl.dict.DataSourceType;
import org.scadalts.e2e.page.impl.dict.UpdatePeriodType;

@Data
public class DataSourceCriteria implements ObjectCriteria {

    private final String identifier;
    private final DataSourceType type;
    private final UpdatePeriodType updatePeriodType;

}
