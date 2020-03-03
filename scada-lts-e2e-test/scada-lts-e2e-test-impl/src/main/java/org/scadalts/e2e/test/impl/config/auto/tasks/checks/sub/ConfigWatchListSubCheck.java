package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.asserts.E2eAssert;
import org.scadalts.e2e.test.impl.config.auto.tasks.sub.SubCommand;

@Data
public class ConfigWatchListSubCheck implements SubCommand<Void> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull DataSourcePointCriteria dataSourcePointCriteria;

    @Override
    public Void execute() {
        WatchListPage watchListPage = navigationPage.openWatchList();
        E2eAssert.assertExists(watchListPage, dataSourcePointCriteria);
        return null;
    }
}
