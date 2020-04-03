package org.scadalts.e2e.cli.options;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import picocli.CommandLine;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ScadaOptions extends TestOptions {

    @CommandLine.Option(names = {"-c", "--alarm-list-changed-after-ms"})
    private int alarmListChangedAfterMs;

    @CommandLine.Option(names = {"-C", "--alarm-list-no-changed-after-ms"})
    private int alarmListNoChangedAfterMs;

    @CommandLine.Option(names = {"-w", "--waiting-after-set-point-value-ms"})
    private int waitingAfterSetPointValueMs;

    @CommandLine.Option(names = {"-g", "--graphical-view-name"})
    private String graphicalViewName;

    @CommandLine.Option(names = {"-e", "--data-point-to-change-xid"})
    private String dataPointToChangeXid;

    @CommandLine.Option(names = {"-E", "--data-point-to-read-xid"})
    private String dataPointToReadXid;

    @CommandLine.Option(names = {"-V", "--point-values-to-tests"}, split = ";", defaultValue = "")
    private String[] dataPointValuesToTests;

    @CommandLine.Option(names = {"-Y", "--data-source-name"})
    private String dataSourceName;

    @CommandLine.Option(names = {"-y", "--data-point-name"})
    private String dataPointName;

    @CommandLine.Option(names = {"-j", "--data-point-target-xid"})
    private String dataPointTargetXid;

    @CommandLine.Option(names = {"-J", "--data-point-source-xid"})
    private String dataPointSourceXid;

}
