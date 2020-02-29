package org.scadalts.e2e.page.core.criterias;

import lombok.Data;
import org.openqa.selenium.By;

@Data
public class ActionCriteria {

    private final CriteriaObject criteria;
    private final By selectAction;

}
