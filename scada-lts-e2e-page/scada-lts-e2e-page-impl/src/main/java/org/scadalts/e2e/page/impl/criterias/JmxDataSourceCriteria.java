package org.scadalts.e2e.page.impl.criterias;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.identifiers.DataSourceIdentifier;
import org.scadalts.e2e.page.impl.dicts.DataSourceType;
import org.scadalts.e2e.page.impl.dicts.UpdatePeriodType;

@Data
public class JmxDataSourceCriteria extends UpdateDataSourceCriteria {

    private final boolean useLocalServer;
    private final String remoteServerAddress;
    private final boolean quantize;
    private final UpdateDataSourceCriteria dataSource;

    @Builder(builderMethodName = "jmxBuilder")
    public JmxDataSourceCriteria(@NonNull UpdateDataSourceCriteria dataSource, boolean useLocalServer, String remoteServerAddress, boolean quantize) {
        super(dataSource);
        this.dataSource = dataSource;
        this.useLocalServer = useLocalServer;
        this.remoteServerAddress = remoteServerAddress;
        this.quantize = quantize;
    }


    public static JmxDataSourceCriteria empty() {
        UpdateDataSourceCriteria updateDataSourceCriteria = UpdateDataSourceCriteria.empty();
        return JmxDataSourceCriteria.jmxBuilder()
                .dataSource(updateDataSourceCriteria)
                .quantize(false)
                .useLocalServer(false)
                .remoteServerAddress("")
                .build();
    }

    public static JmxDataSourceCriteria scadaPerfMonitor() {
        return new JmxDataSourceCriteria(UpdateDataSourceCriteria.criteria(new DataSourceIdentifier("Scada perf monitor [jmx]", DataSourceType.JMX), UpdatePeriodType.SECOND),true,"localhost", false);
    }
}
