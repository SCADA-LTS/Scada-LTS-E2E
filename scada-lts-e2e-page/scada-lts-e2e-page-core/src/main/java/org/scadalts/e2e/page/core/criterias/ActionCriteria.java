package org.scadalts.e2e.page.core.criterias;

import lombok.Data;
import org.openqa.selenium.By;
import org.scadalts.e2e.page.core.criterias.identifiers.NodeCriteria;

@Data
public class ActionCriteria {

    private final NodeCriteria criteria;
    private final By selectAction;

}
