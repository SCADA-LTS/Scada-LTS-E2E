package org.scadalts.e2e.cli.providers;

import org.scadalts.e2e.common.utils.E2eVersionUtil;
import picocli.CommandLine.IVersionProvider;

public class VersionProvider implements IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        return new String[]{E2eVersionUtil.getVersion()};
    }
}
