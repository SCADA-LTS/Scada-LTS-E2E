package org.scadalts.e2e.app.parsers;

import org.scadalts.e2e.common.types.PageLoadStrategy;
import picocli.CommandLine;

public class PageLoadStrategyParser implements CommandLine.ITypeConverter<PageLoadStrategy> {
    @Override
    public PageLoadStrategy convert(String pageLoadStrategy) throws Exception {
        return PageLoadStrategy.valueOf(pageLoadStrategy.toUpperCase());
    }
}
