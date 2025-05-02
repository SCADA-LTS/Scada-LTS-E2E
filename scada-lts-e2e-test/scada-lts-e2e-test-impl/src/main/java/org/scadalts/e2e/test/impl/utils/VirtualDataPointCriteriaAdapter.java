package org.scadalts.e2e.test.impl.utils;

import lombok.EqualsAndHashCode;
import org.scadalts.e2e.page.impl.criterias.VirtualDataPointCriteria;
import org.scadalts.e2e.page.impl.criterias.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataPointIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataPointType;
import org.scadalts.e2e.service.impl.services.datapoint.DataPointPropertiesJson;

@EqualsAndHashCode(callSuper = true)
public class VirtualDataPointCriteriaAdapter extends VirtualDataPointCriteria {

    public VirtualDataPointCriteriaAdapter(DataPointPropertiesJson dataPointPropertiesJson) {
        super(new Xid(dataPointPropertiesJson.getXid()), new DataPointIdentifier(dataPointPropertiesJson.getName(),
                DataPointType.getType(dataPointPropertiesJson.getPointLocator().getType())),
                new ChangeTypeAdapter(dataPointPropertiesJson.getPointLocator()).changeType(),
                dataPointPropertiesJson.getPointLocator().getChangeType().getStartValue(),
                dataPointPropertiesJson.getPointLocator().isSettable(), dataPointPropertiesJson.isEnabled(),
                new DataPointPropertiesAdapter(dataPointPropertiesJson));
    }

}
