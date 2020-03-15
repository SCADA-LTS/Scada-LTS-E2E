package org.scadalts.e2e.page.impl.criterias;

import lombok.*;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.GetXid;
import org.scadalts.e2e.page.core.criterias.Script;
import org.scadalts.e2e.page.core.criterias.identifiers.Xid;
import org.scadalts.e2e.page.impl.criterias.identifiers.ScriptIdentifier;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@ToString
@EqualsAndHashCode
public class ScriptCriteria implements CriteriaObject, GetXid {

    @Deprecated
    private final @NonNull Xid xid;
    private final @NonNull ScriptIdentifier identifier;
    private final @NonNull Script script;
    private final @NonNull @Singular List<DataPointVarCriteria> dataPointVarCriterias;
    private final boolean enableDataPointCommands;
    private final boolean enableDataSourceCommands;

    public static ScriptCriteria dataPointCommandsEnabled(ScriptIdentifier identifier, Script script, DataPointVarCriteria... dataPointVarCriterias) {
        return ScriptCriteria.builder()
                .dataPointVarCriterias(Arrays.asList(dataPointVarCriterias))
                .enableDataPointCommands(false)
                .enableDataPointCommands(true)
                .xid(Xid.xidForScript())
                .script(script)
                .identifier(identifier)
                .build();
    }

    public static ScriptCriteria dataPointCommandsEnabled(Script script, DataPointVarCriteria... dataPointVarCriterias) {
        return ScriptCriteria.builder()
                .dataPointVarCriterias(Arrays.asList(dataPointVarCriterias))
                .enableDataPointCommands(false)
                .enableDataPointCommands(true)
                .xid(Xid.xidForScript())
                .script(script)
                .identifier(IdentifierObjectFactory.scriptName())
                .build();
    }

    public static ScriptCriteria empty() {
        return ScriptCriteria.builder()
                .dataPointVarCriterias(Collections.emptyList())
                .enableDataPointCommands(false)
                .enableDataPointCommands(false)
                .xid(Xid.xidForScript())
                .script(Script.empty())
                .identifier(new ScriptIdentifier(""))
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ScriptCriteria)) return false;
        ScriptCriteria criteria = (ScriptCriteria) o;
        return Objects.equals(getIdentifier(), criteria.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
