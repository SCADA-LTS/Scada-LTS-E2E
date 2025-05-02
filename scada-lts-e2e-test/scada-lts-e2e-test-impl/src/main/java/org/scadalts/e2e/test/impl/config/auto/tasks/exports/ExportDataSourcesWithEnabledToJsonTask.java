package org.scadalts.e2e.test.impl.config.auto.tasks.exports;

import lombok.Data;
import lombok.NonNull;
import org.scadalts.e2e.page.impl.criterias.UpdateDataSourceCriteria;
import org.scadalts.e2e.page.impl.pages.navigation.NavigationPage;
import org.scadalts.e2e.test.impl.config.auto.tasks.Task;
import org.scadalts.e2e.test.impl.utils.JsonUtil;

import java.util.List;

@Data
public class ExportDataSourcesWithEnabledToJsonTask implements Task {

    private final @NonNull NavigationPage navigationPage;

    @Override
    public void execute() {

        List<UpdateDataSourceCriteria> criterias =  navigationPage.openDataSources().getDataSources();
        JsonUtil.serialize(criterias, "export/datasource/criterias.json");
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
