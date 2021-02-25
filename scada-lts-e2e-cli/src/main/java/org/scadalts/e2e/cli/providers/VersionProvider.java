package org.scadalts.e2e.cli.providers;

import org.scadalts.e2e.common.api.E2eCommonApi;
import org.scadalts.e2e.common.core.utils.E2eSystemInfoUtil;
import picocli.CommandLine.IVersionProvider;

public class VersionProvider implements IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        return new String[]{new E2eSystemInfoUtil(E2eCommonApi.createAsciiHeaders()).getInfo()};
    }
}
