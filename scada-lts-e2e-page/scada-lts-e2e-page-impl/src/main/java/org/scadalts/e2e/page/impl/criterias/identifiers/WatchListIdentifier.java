package org.scadalts.e2e.page.impl.criterias.identifiers;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.scadalts.e2e.page.core.criterias.identifiers.AbstractIdentifier;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class WatchListIdentifier extends AbstractIdentifier {

    public static final WatchListIdentifier EMPTY = new WatchListIdentifier("");
    public WatchListIdentifier(@NonNull String value) {
        super(value);
    }

    public static WatchListIdentifier empty() {
        return EMPTY;
    }
}
