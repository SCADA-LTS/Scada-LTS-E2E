package org.scadalts.e2e.cli.options;

import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.Level;
import org.scadalts.e2e.cli.parsers.AuthTypeParser;
import org.scadalts.e2e.cli.parsers.LogLevelParser;
import org.scadalts.e2e.common.types.AuthType;
import picocli.CommandLine;

import java.net.URL;

@Getter
@ToString
public class MainOptions extends DefaultOptions {

    @CommandLine.Option(names = {"-l", "--log-level"}, converter = LogLevelParser.class)
    private Level logLevel;

    @CommandLine.Option(names = {"-u", "--url-app-being-tested"})
    private URL baseUrl;

    @CommandLine.Option(names = {"-U", "--user-name"})
    private String userName;

    @CommandLine.Option(names = {"-p", "--password"})
    private String password;

    @CommandLine.Option(names = {"-a", "--auth-type"}, converter = AuthTypeParser.class)
    private AuthType authType;
}
