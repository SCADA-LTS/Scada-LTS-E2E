package org.scadalts.e2e.page.impl.criterias;

import lombok.NonNull;
import org.scadalts.e2e.page.core.criterias.CriteriaObject;
import org.scadalts.e2e.page.core.criterias.identifiers.IdentifierObject;
import org.scadalts.e2e.page.impl.criterias.identifiers.UserIdentifier;

import java.util.Objects;

public class UserCriteria implements CriteriaObject {

    private final @NonNull UserIdentifier identifier;

    public UserCriteria(@NonNull UserIdentifier identifier) {
        this.identifier = identifier;
    }

    @Override
    public IdentifierObject getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserCriteria)) return false;
        UserCriteria that = (UserCriteria) o;
        return Objects.equals(getIdentifier(), that.getIdentifier());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdentifier());
    }

    @Override
    public String toString() {
        return identifier.toString();
    }
}
