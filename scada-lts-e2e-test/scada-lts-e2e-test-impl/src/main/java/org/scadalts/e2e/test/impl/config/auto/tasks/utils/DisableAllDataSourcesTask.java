package org.scadalts.e2e.test.impl.config.auto.tasks.utils;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.creators.DataSourcePointObjectsCreator;

@Data
public class DisableAllDataSourcesTask implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {
        DataSourcePointObjectsCreator.disableAllDataSources(navigationPage);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
