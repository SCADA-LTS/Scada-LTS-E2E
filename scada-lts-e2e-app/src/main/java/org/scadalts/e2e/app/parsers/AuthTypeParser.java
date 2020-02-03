package org.scadalts.e2e.app.parsers;

import org.scadalts.e2e.common.types.AuthType;
import picocli.CommandLine.ITypeConverter;

public class AuthTypeParser implements ITypeConverter<AuthType> {

    @Override
    public AuthType convert(String authType) throws Exception {
        return AuthType.valueOf(authType.toUpperCase());
    }
}
