package org.scadalts.e2e.test.impl.utils;

import org.scadalts.e2e.page.impl.dicts.ChangeType;
import org.scadalts.e2e.service.impl.services.datapoint.PointLocator;

public class ChangeTypeAdapter {
    private PointLocator pointLocator;

    public ChangeTypeAdapter(PointLocator pointLocator) {
        this.pointLocator = pointLocator;
    }

    public ChangeType changeType() {
        return ChangeType.getType(pointLocator.getChangeType().getType());
    }
}
