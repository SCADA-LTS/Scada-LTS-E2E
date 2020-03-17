package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.DataSourcePointCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;

import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.test.impl.matchers.ContainsObject.containsObject;

@Data
@Log4j2
public class ConfigWatchListSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<DataSourcePointCriteria> dataSourcePointCriterias;

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        WatchListPage watchListPage = navigationPage.openWatchList();
        for(DataSourcePointCriteria dataSourcePointCriteria: dataSourcePointCriterias) {
            if (watchListPage.isVisibleWatchList()) {
                assertThat(watchListPage, containsObject(dataSourcePointCriteria.getIdentifier()));
            } else {
                watchListPage.addToWatchList(dataSourcePointCriteria.getIdentifier());
            }
        }
    }
}
