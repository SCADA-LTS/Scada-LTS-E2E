package org.scadalts.e2e.cli.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AppOptions extends DefaultOptions {

    @CommandLine.Option(names = {"-P", "--port-app"})
    private int portApp;

    @CommandLine.Option(names = {"-q", "--cron-pattern"})
    private String cronPattern;

    @CommandLine.Option(names = {"-Q", "--continuous-mode"}, negatable = true)
    private boolean continuousMode;

    @CommandLine.Option(names = {"-u", "--user-smtp"})
    private String userSmtp;

    @CommandLine.Option(names = {"-p", "--password-smtp"})
    private String passwordSmtp;

    @CommandLine.Option(names = {"-m", "--host-smtp"})
    private String hostSmtp;

    @CommandLine.Option(names = {"-M", "--port-smtp"})
    private int portSmtp;

    @CommandLine.Option(names = {"-s", "--send-to"}, split = ";", defaultValue = "")
    private String[] sendTo;

    @CommandLine.Option(names = {"-S", "--send-from"})
    private String sendFrom;

    @CommandLine.Option(names = {"-d", "--debug-email-mode"}, negatable = true)
    private boolean debugEmailMode;

    @CommandLine.Option(names = {"-n", "--notification-email-mode"}, negatable = true)
    private boolean notificationEmailMode;

    @CommandLine.Option(names = {"-t", "--title-email"})
    private String titleEmail;

    @CommandLine.Option(names = {"-D", "--delete-email-from-sent-emails-after-ms"})
    private long deleteEmailFromSentEmailsAfterMs;

    @CommandLine.Option(names = {"-A", "--mail-smtp-auth-mode"}, negatable = true)
    private boolean mailSmtpAuthMode;

    @CommandLine.Option(names = {"-T", "--mail-smtp-starttls-mode"}, negatable = true)
    private boolean mailSmtpStarttlsMode;
}
