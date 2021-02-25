package org.scadalts.e2e.cli.parsers;

import org.scadalts.e2e.common.core.types.AuthType;
import picocli.CommandLine.ITypeConverter;

public class AuthTypeParser implements ITypeConverter<AuthType> {

    @Override
    public AuthType convert(String authType) throws Exception {
        return AuthType.valueOf(authType.toUpperCase());
    }
}
