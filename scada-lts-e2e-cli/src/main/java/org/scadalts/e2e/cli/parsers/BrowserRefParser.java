package org.scadalts.e2e.cli.parsers;

import org.scadalts.e2e.common.core.types.BrowserRef;
import picocli.CommandLine;

public class BrowserRefParser implements CommandLine.ITypeConverter<BrowserRef> {
    @Override
    public BrowserRef convert(String browser) throws Exception {
        return BrowserRef.valueOf(browser.toUpperCase());
    }
}
