package org.scadalts.e2e.page.impl.criterias;

import lombok.Data;
import org.scadalts.e2e.common.dicts.DictionaryObject;
import org.scadalts.e2e.common.dicts.EmptyType;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.VarIdentifier;

import java.util.Objects;

@Data
public class VarCriteria implements CriteriaObject {

    private final VarIdentifier identifier;
    private final static VarCriteria EMPTY = new VarCriteria(new VarIdentifier(""));

    public static VarCriteria empty() {
        return EMPTY;
    }

    @Override
    public DictionaryObject getType() {
        return EmptyType.ANY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VarCriteria)) return false;
        VarCriteria that = (VarCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getIdentifier());
    }
}
