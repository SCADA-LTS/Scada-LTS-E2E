package org.scadalts.e2e.cli.parsers;

import org.scadalts.e2e.common.core.config.SendTo;
import org.scadalts.e2e.common.core.utils.SendToUtil;
import picocli.CommandLine;

import java.util.*;

public class SendToParser implements CommandLine.ITypeConverter<Set<SendTo>> {

    @Override
    public Set<SendTo> convert(String adresses) throws Exception {
        return SendToUtil.parse(adresses);
    }
}
