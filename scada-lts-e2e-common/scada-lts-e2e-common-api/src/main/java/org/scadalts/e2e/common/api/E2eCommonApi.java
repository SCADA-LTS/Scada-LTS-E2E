package org.scadalts.e2e.common.api;

import org.scadalts.e2e.common.core.utils.AsciiHeaders;
import org.scadalts.e2e.common.impl.logo.AsciiHeadersImpl;

public interface E2eCommonApi {

    static AsciiHeaders createAsciiHeaders() {
        return new AsciiHeadersImpl();
    }
}
