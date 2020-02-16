package org.scadalts.e2e.page.impl.criteria;

import lombok.Data;
import org.scadalts.e2e.page.core.criteria.ObjectCriteria;
import org.scadalts.e2e.page.impl.dict.ChangeType;
import org.scadalts.e2e.page.impl.dict.DataPointType;

@Data
public class DataPointCriteria implements ObjectCriteria {

    private final String identifier;
    private final DataPointType type;
    private final ChangeType changeType;

}
