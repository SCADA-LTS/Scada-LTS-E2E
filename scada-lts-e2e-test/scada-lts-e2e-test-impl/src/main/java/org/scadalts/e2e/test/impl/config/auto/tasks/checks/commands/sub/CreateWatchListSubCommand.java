package org.scadalts.e2e.test.impl.config.auto.tasks.checks.commands.sub;

import lombok.Builder;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.creators.WatchListObjectsCreator;

@Builder
public class CreateWatchListSubCommand implements SubCommand<WatchListCriteria> {

    private final @NonNull NavigationPage navigationPage;
    private final @NonNull WatchListCriteria watchListCriteria;

    @Override
    public WatchListCriteria execute() {
        WatchListObjectsCreator watchListObjectsCreator = new WatchListObjectsCreator(navigationPage, watchListCriteria);
        watchListObjectsCreator.createObjects();
        return watchListCriteria;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
