package org.scadalts.e2e.test.impl.utils;

import lombok.Getter;
import org.scadalts.e2e.page.impl.criteria.WatchListCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.page.impl.pages.watchlist.WatchListPage;
import org.scadalts.e2e.test.core.exceptions.ConfigureTestException;
import org.scadalts.e2e.test.impl.config.TestImplConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class WatchListTestsUtil {

    @Getter
    private WatchListPage watchListPage;
    private WatchListCriteria criteria;

    public WatchListTestsUtil(NavigationPage navigationPage, WatchListCriteria criteria) {
        if(Objects.isNull(navigationPage))
            throw new RuntimeException(new ConfigureTestException());
        this.watchListPage = navigationPage.openWatchList();
        this.criteria = criteria;
    }

    public WatchListPage openWatchListPage() {
        return watchListPage.reopen();
    }

    public void clean() {
        watchListPage.reopen().deleteFromWatchList(criteria);
    }

    public static Collection<String> paramsToTests() {
        String[] values = TestImplConfiguration.dataPointValuesToChangeTests;
        if(!Objects.isNull(values) && values.length > 0)
            return Arrays.asList(values);
        return Arrays.asList( "1" , "2" , "3", "4" , "5", "6", "7",
                "8" , "9" , "10", "11" , "12", "13", "14",
                "15", "16", "17", "18", "19", "20", "21",
                "34552", "342452", "5252", "4523", "553253", "4678675645"
        );
    }
}
