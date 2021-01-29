package org.scadalts.e2e.test.impl.config.auto.tasks.checks.sub;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourcePointIdentifier;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;

import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.scadalts.e2e.test.impl.matchers.ContainsObject.containsObject;

@Data
@Log4j2
public class ConfigWatchListSubCheck implements SubCheck {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull Set<WatchListCriteria> watchListCriterias;

    @Override
    public void check() {
        logger.info("run... {}", this.getClass().getSimpleName());
        WatchListPage watchListPage = navigationPage.openWatchList();
        for(WatchListCriteria watchListCriteria: watchListCriterias) {
            assertThat(watchListPage, containsObject(watchListCriteria.getIdentifier()));
            for(DataSourcePointIdentifier dataSourcePointIdentifier: watchListCriteria.getDataSourcePointIdentifiers()) {
                assertTrue(watchListPage.isVisibleWatchListTable());
                assertTrue(watchListPage.isVisibleWatchListUnit(dataSourcePointIdentifier));
            }
        }

    }
}
